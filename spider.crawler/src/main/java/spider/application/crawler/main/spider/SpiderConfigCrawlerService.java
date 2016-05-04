package spider.application.crawler.main.spider;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.application.crawler.business.IBackListSearchService;
import spider.application.crawler.common.processor.PageParserManager;
import spider.application.crawler.common.processor.SpiderPageProcessor;
import spider.application.crawler.common.utils.CrawlerRequestUtils;
import spider.application.crawler.common.download.SpiderPageDownloader;
import spider.application.crawler.common.pipeline.SpiderCrawlerPipeLine;
import spider.application.crawler.main.spider.parse.SpiderSearchPageParser;
import spider.application.crawler.model.UcsmySite;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Administrator on 2016/4/27.
 */
@Component
public class SpiderConfigCrawlerService implements IBackListSearchService{

    private static Logger logger = LoggerFactory.getLogger(SpiderConfigCrawlerService.class);

    @Autowired
    private SpiderCrawlerPipeLine spiderCrawlerPipeLine;

    @Override
    public void batchFetch(String batchId, Multimap keyList) {
        Map<String,Collection> job = keyList.asMap();
        String sleepRandom = job.get("sleepRandom").iterator().next().toString();
        String domain = job.get("domain").iterator().next().toString();
        String charset = job.get("charset").iterator().next().toString();
        String cycleRetryTime = job.get("cycleRetryTime").iterator().next().toString();
        String sleepTime = job.get("sleepTime").iterator().next().toString();
        String timeOut = job.get("timeOut").iterator().next().toString();
        String userAgent = job.get("userAgent").iterator().next().toString();
        String thread = job.get("thread").iterator().next().toString();
        String SEARCH_PAGE_TMP = job.get("url").iterator().next().toString();
        PageParserManager.registPageParser(SEARCH_PAGE_TMP, new SpiderSearchPageParser());
        List<Request> requestList = new LinkedList<>();
        Collection<Map> requests = keyList.get("request");
        Collection<Map> target = keyList.get("target");
        String method = job.get("method").iterator().next().toString();
        Site site = new UcsmySite()
                .setSleepRandom(Integer.parseInt(sleepRandom))
                .setDomain(domain)
                .setCharset(charset)
                .setCycleRetryTimes(Integer.parseInt(cycleRetryTime))
                .setSleepTime(Integer.parseInt(sleepTime))
                .setTimeOut(Integer.parseInt(timeOut))
                .setUserAgent(userAgent)
                ;
//        site.getHttpProxyPool().enable(true);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String line = Files.readFirstLine(new File("E:\\workspace\\spider.application\\spider.crawler\\target\\test-classes\\20160504\\201605041705"), Charsets.UTF_8);
            Map<String,List<Map>> proxys = objectMapper.readValue(line,Map.class);
            List<String[]> proxyList = Lists.newArrayList();
            for (Map proxy:proxys.get("tb_mayidaili")) {
                if(StringUtils.isNotEmpty(proxy.get("port").toString()));
//                    proxyList.add(new String[]{proxy.get("ip").toString().trim(),proxy.get("port").toString().trim()});
            }
            proxyList.add(new String[]{"111.56.32.72","80"});
            site.setHttpProxyPool(proxyList);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        List<String[]> proxyList = Lists.newArrayList();
//        Lists.newArrayList().add(new String[]{"127.0.0.1","8888"});
//        site.setHttpProxyPool(proxyList);
        Collection<Map> headers = keyList.get("header");
        Iterator<Map> iterator = headers.iterator();
        while ( iterator.hasNext() ) {
            Map<String, Object> header = iterator.next();
            for (Map.Entry<String, Object> entry : header.entrySet()) {
                site.addHeader(entry.getKey(), entry.getValue().toString());
            }
        }


        List<Pipeline> pipelineList = new LinkedList<>();
        spiderCrawlerPipeLine = new SpiderCrawlerPipeLine();
        pipelineList.add(spiderCrawlerPipeLine);
        pipelineList.add(new ConsolePipeline());
        SpiderPageDownloader spiderPageDownloader = new SpiderPageDownloader();

        Spider spider = Spider
                .create(new SpiderPageProcessor(site,batchId,requests,keyList))
                .setDownloader(spiderPageDownloader)
                .setPipelines(pipelineList)
                .setExitWhenComplete(true)
                .thread(Integer.parseInt(thread))
                ;
        iterator = requests.iterator();
        while ( iterator.hasNext() ) {
            Map request = iterator.next();
            requestList.add(CrawlerRequestUtils.createRequest(SEARCH_PAGE_TMP, method,request));

        }
        spider.addRequest(requestList.toArray(new Request[]{}));
        spider.run();
    }
}

package shentong.spider.crawler.main.spider;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shentong.spider.crawler.business.IBackListSearchService;
import shentong.spider.crawler.common.processor.PageParserManager;
import shentong.spider.crawler.common.processor.SpiderPageProcessor;
import shentong.spider.crawler.common.utils.CrawlerRequestUtils;
import shentong.spider.crawler.common.download.SpiderPageDownloader;
import shentong.spider.crawler.common.pipeline.SpiderCrawlerPipeLine;
import shentong.spider.crawler.main.spider.parse.SpiderSearchPageParser;
import shentong.spider.crawler.model.UcsmySite;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.File;
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
            List<String> lines = Files.readLines(new File("d:/123456"), Charsets.UTF_8);
            List<String[]> proxyList = Lists.newArrayList();
            for (String line:lines) {
                List<ArrayList<String>> proxys = objectMapper.readValue(line,List.class);
                for (ArrayList proxy:proxys) {
                    proxyList.add((String[]) proxy.toArray(new String[proxy.size()]));
                }
            }
            site.setHttpProxyPool(proxyList);
        } catch (Exception e) {
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
        pipelineList.add(spiderCrawlerPipeLine);
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

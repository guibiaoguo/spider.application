package spider.application.crawler.common.processor;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spider.application.crawler.model.CrawlerSearchResult;
import spider.application.crawler.model.UcsmySite;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/27.
 */
public class SpiderPageProcessor implements PageProcessor {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Site site;
    private String batchId;
    private Collection<Map> keyList;
    private Multimap target;

    public SpiderPageProcessor(Site site,String batchId,Collection<Map> keyList,Multimap target) {
        this.site = site;
        this.batchId=batchId;
        this.keyList = keyList;
        this.target = target;
    }

    public SpiderPageProcessor(Site site) {
        this.site = site;
    }

    @Override
    public void process(Page page) {
        try {
            if (200 != page.getStatusCode()) {
                throw new RuntimeException(String.format("页面{}抓取失败,响应码{}", String.valueOf(page.getStatusCode()), page.getRequest().getUrl()));
            }
            String url = page.getUrl().get();
            PageParser pageParser = PageParserManager.getPageParser(url);
            Object result = null;
            if (pageParser != null)
                result = pageParser.resolve(page, site,target);

            CrawlerSearchResult crawlerSearchResult = (CrawlerSearchResult) result;
            if(crawlerSearchResult!=null)
            {
                crawlerSearchResult.setBatchId(batchId);
                crawlerSearchResult.setSourceKeyList(keyList);
            }
            page.putField("result", result);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            page.putField("result", PageResultHelper.createFailurePageResult(ex.getMessage()));
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public void setSite(Site site)
    {
        this.site = site;
    }
}


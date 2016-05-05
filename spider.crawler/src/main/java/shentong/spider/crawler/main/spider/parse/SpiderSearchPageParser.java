package shentong.spider.crawler.main.spider.parse;

import com.google.common.collect.Multimap;
import shentong.spider.crawler.common.processor.BasePageParser;
import shentong.spider.crawler.model.CrawlerSearchResult;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

/**
 * Created by ucs_yuananyun on 2016/4/5.
 */
public class SpiderSearchPageParser extends BasePageParser {

    @Override
    public CrawlerSearchResult resolve(Page page, Site site,Multimap target) {
        Multimap multimap = configREsolveToMapList(page,site,target);
        return new CrawlerSearchResult(target.get("candealidentifier").iterator().next().toString(),multimap);
    }

}

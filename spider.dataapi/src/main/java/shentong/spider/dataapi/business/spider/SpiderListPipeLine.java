package shentong.spider.dataapi.business.spider;

import org.springframework.stereotype.Component;
import shentong.spider.dataapi.business.ConfigListBasePipeLine;
import shentong.spider.crawler.model.CrawlerSearchResult;

/**
 * Created by Administrator on 2016/5/5.
 */
@Component
public class SpiderListPipeLine extends ConfigListBasePipeLine{
    @Override
    public boolean processInternal(CrawlerSearchResult searchResult) {
        return false;
    }

    @Override
    public String getStatusUpdateSQL() {
        return null;
    }

    @Override
    public String getCanDealIdentifier() {
        return null;
    }
}

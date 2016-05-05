package shentong.spider.crawler.common.pipeline;


import shentong.spider.crawler.model.CrawlerSearchResult;

/**
 * Created by ucs_yuananyun on 2016/4/6.
 */
public interface ISpiderPipeLine {
    /**
     * 表示此PipeLine能处理的数据标识
     * @return
     */
     String getCanDealIdentifier();

     void process(CrawlerSearchResult searchResult);
}

package shentong.spider.proxy.business;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import shentong.spider.crawler.common.pipeline.ISpiderPipeLine;
import shentong.spider.crawler.model.CrawlerSearchResult;

import java.util.List;
import java.util.Map;


/**
 * Created by ucs_sukun on 2016/4/6.
 */
@Component
public abstract class ConfigListBasePipeLine implements ISpiderPipeLine {
    private Logger logger = LoggerFactory.getLogger(ConfigListBasePipeLine.class);


    @Override
    public void process(CrawlerSearchResult searchResult) {
        if(!getCanDealIdentifier().equals(searchResult.getDataIdentifier())) return;
        //修改该批次的状态位
        Multimap data = searchResult.getData();
//        if (data == null || data.size() == 0) return;

        try {
            if (data != null && data.size() > 0) {
                processInternal(searchResult);
                logger.info(String.format("%s处理了属于批次%s的%s条数据", getCanDealIdentifier(), searchResult.getBatchId(), data.entries().size()));
            }
        }catch ( Exception e) {
            e.printStackTrace();
        }
    }

    public abstract boolean processInternal(CrawlerSearchResult searchResult);


    public abstract String getStatusUpdateSQL();

}

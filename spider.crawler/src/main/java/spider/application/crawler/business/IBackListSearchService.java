package spider.application.crawler.business;

import com.google.common.collect.Multimap;

import java.util.List;

/**
 * Created by ucs_yuananyun on 2016/4/6.
 */
public interface IBackListSearchService {
    void batchFetch(String batchId, Multimap keyList) ;
}

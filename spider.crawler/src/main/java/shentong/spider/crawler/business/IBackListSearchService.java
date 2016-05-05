package shentong.spider.crawler.business;

import com.google.common.collect.Multimap;

import java.io.IOException;
import java.util.List;

/**
 * Created by ucs_yuananyun on 2016/4/6.
 */
public interface IBackListSearchService {
    void batchFetch(String batchId, Multimap keyList);
}

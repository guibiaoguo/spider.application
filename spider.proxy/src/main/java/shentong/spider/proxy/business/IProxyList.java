package shentong.spider.proxy.business;

import com.google.common.collect.Multimap;

/**
 * Created by Administrator on 2016/5/5.
 */
public interface IProxyList {
    void batchFetch(Multimap multimap);
}

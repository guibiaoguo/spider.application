package shentong.spider.crawler.common.processor;

import com.google.common.collect.Multimap;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;


/**
 * 自定义的页面解析器
 * Created by ucs_yuananyun on 2016/3/29.
 */
public interface PageParser {

    /**
     * 解析页面并返回解析结果
     *
     * @param page
     * @param site
     * @return
     */
    <T> T  resolve(Page page, Site site,Multimap target);

}

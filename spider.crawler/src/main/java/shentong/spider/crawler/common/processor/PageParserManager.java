package shentong.spider.crawler.common.processor;

import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import shentong.spider.crawler.common.utils.UrlUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 页面解析器管理者
 * Created by ucs_yuananyun on 2016/3/29.
 */
public class PageParserManager {
    private static Map<String, PageParser> pageParserMap = new HashMap<>();
    private static PageParser defaultPageParser = new BasePageParser() {

        @Override
        public <T> T  resolve(Page page, Site site, Multimap target) {
            return null;
        }
    };

    public static boolean registPageParser(String key, PageParser parser) {
        if (StringUtils.isBlank(key)) return false;
        pageParserMap.put(UrlUtils.getContextPath(key), parser);
        return true;
    }

    public static boolean removePageParser(String key) {
        return pageParserMap.remove(key) != null;
    }

    public static PageParser getPageParser(String key) {
        PageParser parser = pageParserMap.get(UrlUtils.getContextPath(key));
        if (parser == null) {
            for (String k : pageParserMap.keySet()) {
                Pattern pattern = Pattern.compile(k);
                if (pattern.matcher(key).find()) {
                    parser = pageParserMap.get(UrlUtils.getContextPath(k));
                    break;
                }
            }
        }
        return parser == null ? defaultPageParser : parser;
    }

}

package shentong.spider.crawler.common.utils;

import org.apache.commons.lang3.StringUtils;
import us.codecraft.webmagic.selector.Selectable;

/**
 * Created by ucs_yuananyun on 2016/3/29.
 */
public class RegexResolveUtils {
    public static  String resolveSingleRegexValue(Selectable node,String regex) {
        if(StringUtils.isBlank(regex)) return null;
        Selectable selectable = node.regex(regex);
        if(!selectable.match()) return null;
        return selectable.get().trim();
    }
}

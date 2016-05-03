package spider.application.crawler.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by ucs_yuananyun on 2016/3/29.
 */
public class UrlUtils {

    /**
     * 根据给定的Url模版和参数生成一个完整的Url
     *
     * @param template
     * @param keywords
     * @return
     */
    public static String generateUrl(String template, Object... keywords) {
        Object[] valueArray = new String[keywords.length];
        for (int i = 0; i < keywords.length; i++) {
            try {
                valueArray[i] = URLEncoder.encode(String.valueOf(keywords[i]), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                valueArray[i] = keywords[i];
            }
        }
        return String.format(template, valueArray);
    }

    /**
     * 获取给的url的不含请求参数的部分
     *
     * @param url
     * @return
     */
    public static String getContextPath(String url) {
        if (StringUtils.isBlank(url)) return url;
        int index = url.indexOf("?");
        if (index > 0)
            return url.substring(0, index);
        return url;
    }

}

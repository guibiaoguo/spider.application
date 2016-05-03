package spider.application.crawler.common.processor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ucs_yuananyun on 2016/3/31.
 */
public class PageResultHelper {
    public static Map<String, Object> createSuccessPageResult(Object data,Map<String,Object> extraInfo) {
        Map<String, Object> result = new HashMap<>();
        result.put("state", "ok");
        result.put("message", "");
        result.put("data", data);
        if(extraInfo!=null)
        result.putAll(extraInfo);
        return result;
    }

    public static Map<String, Object> createFailurePageResult(String message) {
        Map<String, Object> result = new HashMap<>();
        result.put("state", "error");
        result.put("message", message);
        result.put("data", null);
        return result;
    }

    public static <T> T getData(Object o)
    {
        if(Map.class.isAssignableFrom(o.getClass())) return (T)o;
        return (T) ((Map) o).get("data");
    }

}

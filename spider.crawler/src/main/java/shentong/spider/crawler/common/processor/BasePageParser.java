package shentong.spider.crawler.common.processor;

import com.google.common.collect.*;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shentong.spider.crawler.common.download.SpiderPageDownloader;
import shentong.spider.crawler.common.utils.DateUtils;
import shentong.spider.crawler.common.utils.CrawlerRequestUtils;
import shentong.spider.ocr.main.OCRHelper;
import shentong.spider.ocr.util.ImageUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.selector.Json;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.selector.Selectable;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.File;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by ucs_yuananyun on 2016/3/30.
 */
public abstract class BasePageParser implements PageParser {
    private static Downloader pageDownLoader = new SpiderPageDownloader();
    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 对给定的dom使用正则表达式取值到map
     *
     * @param domRegion
     * @param regexMap
     * @return
     */
    protected final Map<String, Object> regexResolveToMap(Selectable domRegion, Map<String, String> regexMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : regexMap.entrySet()) {
            map.put(entry.getKey(), resolveSingleRegexValue(domRegion, entry.getValue()));
        }
        return map;
    }

    public final static String resolveSingleRegexValue(Selectable node, String regex) {
        if (StringUtils.isBlank(regex)) return null;
        if (!regex.contains("(.*?)")) return regex;

        Selectable selectable = node.regex(regex);
        if (!selectable.match()) return "无";
        return selectable.get().trim();
    }

    public final static String resolveSingleJsonValue(Selectable node, String json) {
        try {
            if (StringUtils.isBlank(json)) return null;
            Selectable selectable = node.jsonPath(json);
            if (!selectable.match()) return "无";
            return selectable.get().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对给定的dom使用正则表达式取值到map集合
     *
     * @param domRegion
     * @param selector
     * @param regexMap
     * @return
     */
    public List<Map<String, Object>> regexResolveToMapList(Selectable domRegion, String selector, Map<String, String> regexMap) {
        List<Selectable> childDomList = domRegion.$(selector).nodes();
        return regexResolveToMapList(childDomList, regexMap);
    }

    public List<Map<String, Object>> regexResolveToMapList(List<Selectable> targetDomList, Map<String, String> regexMap) {
        if (targetDomList.size() > 0) {
            List<Map<String, Object>> itemMapList = new LinkedList<>();
            for (Selectable childDom : targetDomList) {
                itemMapList.add(regexResolveToMap(childDom, regexMap));
            }
            return itemMapList;
        }
        return null;
    }

    public List<Map<String, Object>> jsonResolveToMapList(Selectable domRegion, String selector, Map<String, String> personBlackRegxMap) {
        List<Selectable> childDomList = domRegion.jsonPath(selector).nodes();
        return jsonResolveToMapList(childDomList, personBlackRegxMap);
    }

    public List<Map<String, Object>> jsonResolveToMapList(List<Selectable> targetDomList, Map<String, String> jsonMap) {
        if (targetDomList.size() > 0) {
            List<Map<String, Object>> itemMapList = new LinkedList<>();
            for (Selectable childDom : targetDomList) {
                itemMapList.add(jsonResolveToMap(childDom, jsonMap));
            }
            return itemMapList;
        }
        return null;
    }

    /**
     * 对给定的dom使用JsonPath取值到map
     *
     * @param domRegion
     * @param jsonMap
     * @return
     */
    protected final Map<String, Object> jsonResolveToMap(Selectable domRegion, Map<String, String> jsonMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        domRegion = new Json(domRegion.get());
        for (Map.Entry<String, String> entry : jsonMap.entrySet()) {
            map.put(entry.getKey(), resolveSingleJsonValue(domRegion, entry.getValue()));
        }
        return map;
    }

    public List<Map<String, Object>> xpathResolveToMapList(Selectable domRegion, String selector, Map<String, String> regexMap) {
        List<Selectable> childDomList = domRegion.$(selector).nodes();
        return xpathResolveToMapList(childDomList, regexMap);
    }

    public List<Map<String, Object>> xpathResolveToMapList(List<Selectable> targetDomList, Map<String, String> regexMap) {
        if (targetDomList.size() > 0) {
            List<Map<String, Object>> itemMapList = new LinkedList<>();
            for (Selectable childDom : targetDomList) {
                itemMapList.add(xpathResolveToMap(childDom, regexMap));
            }
            return itemMapList;
        }
        return null;
    }

    protected final Map<String, Object> xpathResolveToMap(Selectable domRegion, Map<String, String> xpathMap) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Map.Entry<String, String> entry : xpathMap.entrySet()) {
            String value = entry.getValue();
            if (StringUtils.isBlank(value))
                map.put(entry.getKey(), null);
            else
                map.put(entry.getKey(), domRegion.xpath(value));
        }
        return map;
    }

    /**
     * 下载一个页面
     *
     * @param orginSite
     * @param targetUrl
     * @return
     */
    protected Page downloadPage(final Site orginSite, String targetUrl, String method, Map<String, String> params) {
        Request request = CrawlerRequestUtils.createRequest(targetUrl, method, params);
        return pageDownLoader.download(request, new Task() {
            @Override
            public String getUUID() {
                return UUID.randomUUID().toString();
            }

            @Override
            public Site getSite() {
                return orginSite;
            }
        });
    }

    protected Page downloadPage(final Site orginSite, String targetUrl) {
        return downloadPage(orginSite, targetUrl, HttpConstant.Method.GET, null);
    }

    public Selectable configSingleResolve(Selectable tmpSelectable, String express, Page page) {

        String[] parameters = StringUtils.split(express, ";");
        Method method;
        for (int i = 0; i < parameters.length; i++) {
            String temp = StringUtils.substring(parameters[i], parameters[i].indexOf("(") + 1, parameters[i].lastIndexOf(")"));
            String[] select = StringUtils.split(temp, "$$");
            Object[] parVal = new Object[select.length];
            List<Class> clses = new ArrayList<>();
            for (int j = 0; j < select.length; j++) {
                try {
                    parVal[j] = Integer.parseInt(select[j]);
                    clses.add(int.class);
                } catch (Exception e) {
                    parVal[j] = select[j];
                    clses.add(String.class);
                }
            }
            try {
                if (StringUtils.startsWith(parameters[i], "split")) {
                    List<String> list = new ArrayList<>();
                    Object object = tmpSelectable.all();
                    if (object instanceof List) {
                        List<Object> items = (List<Object>) object;
                        int count = (Integer) parVal[1];
                        for (Object item : items) {
                            String tmp = String.valueOf(item).split(parVal[0].toString())[count];
                            list.add(String.valueOf(tmp));
                        }
                    } else {
                        list.add(String.valueOf(object));
                    }
                    tmpSelectable = new PlainText(list);
                } else if (StringUtils.startsWith(parameters[i], "final")) {
                    tmpSelectable = new PlainText(parVal[0].toString());
                } else if (StringUtils.startsWith(parameters[i], "saveFile")) {
                    CloseableHttpClient httpClient = page.getResultItems().get("$httpclient");
//                    HttpGet httpget = new HttpGet(tmpSelectable.get());
//                    httpget.setConfig(RequestConfig.custom().setProxy(new HttpHost("127.0.0.1", 8888)).build());
//                    httpget.setHeader(new BasicHeader("Cookie", "proxy_token=" + page.getHtml().xpath("//script").regex("proxy_token=(.*?);")));
//                    CloseableHttpResponse response = httpClient.execute(httpget);
                    byte[] bytes = saveFile(httpClient,tmpSelectable.get(),ImmutableMap.of("Cookie","proxy_token=" + page.getHtml().xpath("//script").regex("proxy_token=(.*?);")));
                    ObjectMapper objectMapper = new ObjectMapper();
                    tmpSelectable = new PlainText(objectMapper.writeValueAsString(bytes));
                } else if (StringUtils.startsWith(parameters[i], "saveAndOCRFile")) {
                    CloseableHttpClient httpClient = page.getResultItems().get("$httpclient");
                    byte[] bytes = saveFile(httpClient,tmpSelectable.get(),ImmutableMap.of("Cookie","proxy_token=" + page.getHtml().xpath("//script").regex("proxy_token=(.*?);")));
                    String file = getClass().getResource("/").getPath() + DateUtils.currtimeToString8() + "/" + select[0] + "_" + DateUtils.currtimeToString17() + select[1];
                    Files.createParentDirs(new File(file));
                    Files.write(bytes, new File(file));
                    String tmpFileName = ImageUtil.printImage(file,-16777216);
                    File tmpFile = new File(tmpFileName);
                    String text = new OCRHelper().recognizeText(tmpFile).trim();
                    tmpSelectable = new PlainText(text);
                    tmpFile.delete();
                } else {
                    //获取方法方法名调用
                    method = tmpSelectable.getClass().getMethod(StringUtils.substring(parameters[i], 0, parameters[i].indexOf("(")), clses.toArray(new Class[clses.size()]));
                    tmpSelectable = (Selectable) method.invoke(tmpSelectable, parVal);
                }
            } catch (Exception e) {
                tmpSelectable = new PlainText("");
                e.printStackTrace();
                logger.info("参数不存在 " + e.getMessage());
            }
        }
        return tmpSelectable;
    }

    private byte[] saveFile(CloseableHttpClient httpClient,String url,Map<String,String> headers) throws Exception{
        HttpGet httpget = new HttpGet(url);
        httpget.setConfig(RequestConfig.custom().setProxy(new HttpHost("127.0.0.1", 8888)).build());
        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpget.setHeader(new BasicHeader(header.getKey(),header.getValue().toString()));
        }
        CloseableHttpResponse response = httpClient.execute(httpget);
        byte[] bytes = EntityUtils.toByteArray(response.getEntity());
        return bytes;
    }
    public Map configResolveToMap(Selectable selectable, Map<String, Object> configMaps, Page page) {
        Map multimap = Maps.newHashMap();
        String table = configMaps.get("$table") != null ? configMaps.get("$table").toString() : null;
        for (Map.Entry<String, Object> configMap : configMaps.entrySet()) {
            if (!StringUtils.startsWith(configMap.getKey(), "$")) {
                String express = configMap.getValue().toString();
                multimap.put(configMap.getKey(), configSingleResolve(selectable, express, page).get().trim());
            }
        }

        return multimap;
    }

    public Multimap configREsolveToMapList(Page page, Site site, Multimap target) {
        Multimap multimap = ArrayListMultimap.create();
        Request request = page.getRequest();
        String contentType = target.get("contentType").iterator().next().toString();
//        Collection<Map> targetTasks = target.get("targetTasks");
        Selectable selectable = null;
        Method method;
        CloseableHttpClient httpClient = page.getResultItems().get("$httpclient");
        if (contentType == "0") {
            selectable = page.getHtml();
        } else {
            selectable = page.getJson();
        }
        Iterator<Map> tasks = target.get("tasks").iterator();
        while (tasks.hasNext()) {
            Map<String, Object> tmpTasks = tasks.next();

            String module = tmpTasks.get("$module").toString();
            selectable = configSingleResolve(selectable, module, page);
            String table = tmpTasks.get("$table").toString();
            String flag = tmpTasks.get("$flag").toString();

            for (Selectable sel : selectable.nodes()) {
                Map map = configResolveToMap(sel, tmpTasks, page);
                if (StringUtils.equalsIgnoreCase(flag, "1"))
                    multimap.put(table, map);
                else
                    page.addTargetRequest(CrawlerRequestUtils.createRequest(target.get("url").iterator().next().toString(), target.get("method").iterator().next().toString(), map));
            }

        }

        return multimap;
    }

}

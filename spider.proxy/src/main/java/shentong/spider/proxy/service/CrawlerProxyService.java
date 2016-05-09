package shentong.spider.proxy.service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shentong.spider.proxy.main.maiyidaili.MaYiProxyList;
import shentong.spider.proxy.utils.HttpUtil;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/6.
 */
@Component
@Service
public class CrawlerProxyService {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private MaYiProxyList maYiProxyList;

    private Logger logger = LoggerFactory.getLogger(getClass());

    public void batchFetch() {
        Multimap multimap = ArrayListMultimap.create();
        multimap.put("sleepRandom", "0");
        multimap.put("domain", "mayidaili.com");
        multimap.put("charset", "utf-8");
        multimap.put("cycleRetryTime", "3");
        multimap.put("sleepTime", 1000);
        multimap.put("timeOut", 0);
        multimap.put("userAgent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
        multimap.put("thread", "20");
        multimap.put("url", "http://www.mayidaili.com/free/");
        multimap.put("method", HttpConstant.Method.GET);
        multimap.put("contentType", "0");
        for (Integer i = 1; i < 1260; i++) {
            multimap.put("request", ImmutableMap.of("", i.toString()));
        }
        multimap.put("candealidentifier", "MaYiProxy");
        Map task = Maps.newHashMap();
        task.put("$flag", "1");
        task.put("$table", "tb_mayidaili");
        task.put("$module", "xpath(//div[@class=\"col-md-9\"]//tbody/tr)");
        task.put("port", "xpath(//td[2]/img/@data-uri);saveAndOCRFile(port$$.png);");
        task.put("ip", "xpath(//td[1]/text());");
        task.put("anonymity", "xpath(//td[3]/allText())");
        task.put("country", "xpath(//td[4]/allText())");
        task.put("address", "xpath(//td[5]/allText())");
        task.put("connection_time", "xpath(//td[6]/allText())");
        task.put("release_time", "xpath(//td[7]/allText())");
        multimap.put("tasks", task);
        maYiProxyList.batchFetch(multimap);
    }

    public void releaseProxy() {
        HttpResponse response = null;
        HttpUtil httpUtil = HttpUtil.getInstance();
        List<Map> proxys = sqlSessionTemplate.selectList("selectProxy");
        for (Map proxy : proxys) {
            try {
                String host = proxy.get("ip").toString().trim();
                String port = proxy.get("port").toString().trim();
                if (StringUtils.isNotEmpty(port))
                    response = httpUtil.doGet("http://www.haosou.com", null, null, proxy);
                if (response.getStatusLine().getStatusCode() != 200) {
                    sqlSessionTemplate.delete("deleteProxy",proxy);
                }
            } catch (Exception e) {
                sqlSessionTemplate.delete("deleteProxy",proxy);
                logger.info(e.getMessage());
            }
        }


    }
}

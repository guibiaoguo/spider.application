package shentong.spider.crawler.main;

import com.google.common.collect.*;
import org.junit.Test;
import shentong.spider.crawler.main.spider.SpiderConfigCrawlerService;
import us.codecraft.webmagic.utils.HttpConstant;

import java.util.*;

/**
 * Created by Administrator on 2016/4/28.
 */

public class SpiderListStarterTest {

    @Test
    public void startCrawler() {
        SpiderConfigCrawlerService spiderListStarter = new SpiderConfigCrawlerService();
        Multimap multimap = ArrayListMultimap.create();
        multimap.put("sleepRandom","0");
        multimap.put("domain","mayidaili.com");
        multimap.put("charset","utf-8");
        multimap.put("cycleRetryTime","3");
        multimap.put("sleepTime",1000);
        multimap.put("timeOut",0);
        multimap.put("userAgent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
        multimap.put("thread","1");
        multimap.put("url","http://www.mayidaili.com/free/");
        multimap.put("method", HttpConstant.Method.GET);
        multimap.put("contentType","0");
//        for (Integer i = 1; i < 20; i++) {
//            multimap.put("request", ImmutableMap.of("",i.toString()));
//        }
        multimap.put("request", ImmutableMap.of("","1"));
//        multimap.put("request", ImmutableMap.of("","2"));
//        multimap.put("request", ImmutableMap.of("","3"));
//        multimap.put("request", ImmutableMap.of("","4"));
//        multimap.put("request", ImmutableMap.of("","5"));
//        multimap.put("request", ImmutableMap.of("","6"));
//        multimap.put("request", ImmutableMap.of("","7"));
//        multimap.put("request", ImmutableMap.of("","8"));
//        multimap.put("module","//div[@class=\"col-md-9\"]//tbody/tr");
        multimap.put("tasks",ImmutableMap.of("$flag","1","$table","tb_mayidaili","$module","xpath(//div[@class=\"col-md-9\"]//tbody/tr)","port","xpath(//td[2]/img/@data-uri);saveAndOCRFile(port$$.png);","ip","xpath(//td[1]/text());"));
//        multimap.put("targetTasks", ImmutableMap.of("express","xpath(//td[1]/text());","key","ip","table","tb_mayidaili"));
//        multimap.put("targetTasks", ImmutableMap.of("express","xpath(//*[@id=\"list\"]/table/tbody/tr/td[2]/text());","key","port","table","tb_kuaidaili"));
//        multimap.put("targetTasks", ImmutableMap.of("express","css(#listnav li:has(a.active)+li>a$$text);","key",""));
//        multimap.put("targetTasks", ImmutableMap.of("express","xpath(//td[2]/img/@data-uri);saveFile(port$$.png);","key","$file","table","tb_mayidaili"));
        spiderListStarter.batchFetch(UUID.randomUUID().toString(),multimap);

    }
}

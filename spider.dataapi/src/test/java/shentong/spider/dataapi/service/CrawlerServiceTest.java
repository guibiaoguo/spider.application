package shentong.spider.dataapi.service;

import com.google.common.base.Charsets;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import shentong.spider.dataapi.Application;
import us.codecraft.webmagic.utils.HttpConstant;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CrawlerServiceTest {

    @Autowired
    private CrawlerService crawlerService;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Test
    public void startPersonBlackListCrawler() {
        Multimap multimap = ArrayListMultimap.create();
        multimap.put("sleepRandom","0");
        multimap.put("domain","mayidaili.com");
        multimap.put("charset","utf-8");
        multimap.put("cycleRetryTime","3");
        multimap.put("sleepTime",1000);
        multimap.put("timeOut",0);
        multimap.put("userAgent","Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.109 Safari/537.36");
        multimap.put("thread","2");
        multimap.put("url","http://www.mayidaili.com/free/");
        multimap.put("method", HttpConstant.Method.GET);
        multimap.put("contentType","0");
        for (Integer i = 1; i < 40; i++) {
            multimap.put("request", ImmutableMap.of("",i.toString()));
        }
        List<Map> proxys = sqlSessionTemplate.selectList("selectProxy");
        for (Map proxy : proxys) {
            multimap.put("proxy",new String[]{proxy.get("ip").toString(),proxy.get("port").toString()});
        }
//                ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            List<String> lines = Files.readLines(new File("d:/123456"), Charsets.UTF_8);
//            List<String[]> proxyList = Lists.newArrayList();
//            for (String line:lines) {
//                List<ArrayList<String>> proxys = objectMapper.readValue(line,List.class);
//                for (ArrayList proxy:proxys) {
//                    multimap.put("proxy", proxy.toArray(new String[proxy.size()]));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        multimap.put("request", ImmutableMap.of("","1"));
//        multimap.put("request", ImmutableMap.of("","2"));
//        multimap.put("request", ImmutableMap.of("","3"));
//        multimap.put("request", ImmutableMap.of("","4"));
//        multimap.put("request", ImmutableMap.of("","5"));
//        multimap.put("request", ImmutableMap.of("","6"));
//        multimap.put("request", ImmutableMap.of("","7"));
//        multimap.put("request", ImmutableMap.of("","8"));
//        multimap.put("module","//div[@class=\"col-md-9\"]//tbody/tr");
        multimap.put("candealidentifier","MaYiProxy");
        multimap.put("tasks",ImmutableMap.of("$flag","1","$table","tb_mayidaili","$module","xpath(//div[@class=\"col-md-9\"]//tbody/tr)","port","xpath(//td[2]/img/@data-uri);saveAndOCRFile(port$$.png);","ip","xpath(//td[1]/text());"));
//        multimap.put("targetTasks", ImmutableMap.of("express","xpath(//td[1]/text());","key","ip","table","tb_mayidaili"));
//        multimap.put("targetTasks", ImmutableMap.of("express","xpath(//*[@id=\"list\"]/table/tbody/tr/td[2]/text());","key","port","table","tb_kuaidaili"));
//        multimap.put("targetTasks", ImmutableMap.of("express","css(#listnav li:has(a.active)+li>a$$text);","key",""));
//        multimap.put("targetTasks", ImmutableMap.of("express","xpath(//td[2]/img/@data-uri);saveFile(port$$.png);","key","$file","table","tb_mayidaili"));
        crawlerService.startPersonBlackListCrawler(multimap);
    }


}

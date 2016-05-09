package shentong.spider.dataapi.service;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import shentong.spider.crawler.common.utils.MapBuilder;
import shentong.spider.crawler.main.SpiderListStarter;
import shentong.spider.crawler.main.spider.SpiderConfigCrawlerService;
import shentong.spider.dataapi.utils.HttpUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by ucs_yuananyun on 2016/3/31.
 */
@Component
public class CrawlerService {
    private static Logger logger = LoggerFactory.getLogger(CrawlerService.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    @Autowired
    private SpiderListStarter spiderListStarter;

    public void startPersonBlackListCrawler(Multimap multimap) {
        HttpUtil httpUtil = HttpUtil.getInstance();
        try {
            Map<String,Object> version = httpUtil.doGetForMap("http://101.201.153.66/spider/regedit",MapBuilder.instance().put("agentID","2016").put("version","3.0").map(),null);
            Map<String,Object> job = httpUtil.doGetForMap("http://101.201.153.66/job/spider/",MapBuilder.instance().put("",version.get("uuid")).map(),null);
            Multimap data = ArrayListMultimap.create();

        } catch ( Exception e) {
            e.printStackTrace();
        }
        spiderListStarter.startCrawler(UUID.randomUUID().toString(),multimap);
    }

}

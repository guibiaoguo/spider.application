package shentong.spider.dataapi.service;
import com.google.common.collect.Multimap;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shentong.spider.crawler.common.utils.MapBuilder;
import shentong.spider.crawler.main.SpiderListStarter;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_yuananyun on 2016/3/31.
 */
@Service
public class CrawlerService {
    private static Logger logger = LoggerFactory.getLogger(CrawlerService.class);
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;


    @Autowired
    private SpiderListStarter blackListStarter;

    public void startPersonBlackListCrawler(Multimap multimap) {
        List<Map<String, Object>> personLists = sqlSessionTemplate.selectList("selectPersonList", MapBuilder.instance().put("status", 0).map());
        int batchSize = 10;
        for (Map map : personLists) {
            int size = Integer.parseInt(map.get("countNum").toString());
            String batchId = map.get("batchId").toString();
            int batchCount = (size % batchSize == 0) ? (size / batchSize) : ((size / batchSize) + 1);
            blackListStarter.startCrawler("p2pblack",multimap);

        }
    }

}

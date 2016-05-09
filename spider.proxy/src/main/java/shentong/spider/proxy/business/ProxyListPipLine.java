package shentong.spider.proxy.business;

import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shentong.spider.crawler.model.CrawlerSearchResult;
import shentong.spider.proxy.model.ProxyPool;
import shentong.spider.proxy.utils.HttpUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/5.
 */
@Component
public class ProxyListPipLine extends ConfigListBasePipeLine{

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Autowired
    private ProxyPool proxyPool;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean processInternal(CrawlerSearchResult searchResult) {
        Multimap multimap = searchResult.getData();
        Iterator<Map> iterator = multimap.get("tb_mayidaili").iterator();
        HttpUtil httpUtil = HttpUtil.getInstance();
        List<Map> proxys = Lists.newArrayList();
        while (iterator.hasNext()) {
            Map proxy = iterator.next();
            HttpResponse response = null;
            try {
                String host = proxy.get("ip").toString().trim();
                String port = proxy.get("port").toString().trim();
                if(StringUtils.isNotEmpty(port))
                    response = httpUtil.doGet("http://www.haosou.com",null,null,proxy);
                if(response != null && response.getStatusLine().getStatusCode() == 200) {
                    proxys.add(proxy);
                } else {

                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }

        }
        if(proxys.size() > 0) {
            logger.info("更新" + proxys.size() + "条数据");
            sqlSessionTemplate.insert("addProxy",proxys);
        }
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String content = objectMapper.writeValueAsString(proxys);
//            File dataFile = new File("d:/123456");
//            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataFile, true));
//            bufferedWriter.write(content + "\n");
//            bufferedWriter.flush();
//            bufferedWriter.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return false;
    }

    @Override
    public String getStatusUpdateSQL() {
        return null;
    }

    @Override
    public String getCanDealIdentifier() {
        return "MaYiProxy";
    }

}

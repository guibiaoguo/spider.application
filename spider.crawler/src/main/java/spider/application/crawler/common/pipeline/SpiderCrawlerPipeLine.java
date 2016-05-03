package spider.application.crawler.common.pipeline;

import com.google.common.base.Charsets;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spider.application.crawler.common.utils.DateUtils;
import spider.application.crawler.model.CrawlerSearchResult;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_yuananyun on 2016/4/6.
 */
@Component
public class SpiderCrawlerPipeLine implements Pipeline {

    @Autowired
    private List<ISpiderPipeLine> pipeLineList;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void process(ResultItems resultItems, Task task) {
//        if (pipeLineList == null || pipeLineList.size() == 0) return;
//
        CrawlerSearchResult data = resultItems.get("result");
        Multimap multimap = data.getData();
        Iterator< Map<String,List<String>>> iterator = multimap.get("tb_kuaidaili").iterator();
        Map<String,List<String>> map = iterator.next();
        System.out.println(map.get("ip").get(0) + ":");
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String tmpfile = getClass().getResource("/").getPath() + DateUtils.currtimeToString8() + "/" + DateUtils.currtimeToString12();
            Files.createParentDirs(new File(tmpfile));
            File dataFile = new File(tmpfile);
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(dataFile, true));
            bufferedWriter.write(objectMapper.writeValueAsString(multimap.asMap()) + "\n");
            bufferedWriter.flush();
            bufferedWriter.close();
//            objectMapper.writeValue(new File(tmpfile),multimap.asMap());
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        for (final ISpiderPipeLine spiderPipeLine : pipeLineList) {
//            if (data.getDataIdentifier().equals(spiderPipeLine.getCanDealIdentifier()))
//                spiderPipeLine.process(data);
//        }
    }
}

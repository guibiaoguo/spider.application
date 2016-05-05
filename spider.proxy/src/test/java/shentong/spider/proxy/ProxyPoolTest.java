package shentong.spider.proxy;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import shentong.spider.proxy.model.ProxyPool;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/4/27.
 */
public class ProxyPoolTest {

    private ProxyPool proxyPool;

    @Test
    public void testProxy() throws IOException {
        ProxyPool proxyPool = new ProxyPool();
                ObjectMapper objectMapper = new ObjectMapper();
        try {
            String line = Files.readFirstLine(new File("E:\\workspace\\spider.application\\spider.crawler\\target\\test-classes\\20160504\\201605041655"), Charsets.UTF_8);
            Map<String,List<Map>> proxys = objectMapper.readValue(line,Map.class);
            List<String[]> proxyList = Lists.newArrayList();
            for (Map proxy:proxys.get("tb_mayidaili")) {
                if(StringUtils.isNotEmpty(proxy.get("port").toString()))
                    proxyList.add(new String[]{proxy.get("ip").toString().trim(),proxy.get("port").toString().trim()});
            }
            String json = objectMapper.writeValueAsString(proxyPool.getProxyQueue(proxyList));
            System.out.println(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

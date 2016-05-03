package shentong.application.proxy;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import spider.application.proxy.ProxyPool;

import java.io.IOException;

/**
 * Created by Administrator on 2016/4/27.
 */
public class ProxyPoolTest {

    private ProxyPool proxyPool;

    @Test
    public void testProxy() throws IOException {
        ProxyPool proxyPool = new ProxyPool();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(proxyPool.getProxyQueue(ProxyPool.getProxyList()));
        System.out.println(json);
    }
}

package shentong.spider.proxy.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import shentong.spider.proxy.Application;

/**
 * Created by Administrator on 2016/5/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CrawlerProxyServiceTest {

    @Autowired
    private CrawlerProxyService crawlerProxyService;

    @Test
    public void testReleaseProxy() {
        crawlerProxyService.releaseProxy();
    }
}

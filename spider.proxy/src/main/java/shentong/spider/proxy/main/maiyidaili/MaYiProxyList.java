package shentong.spider.proxy.main.maiyidaili;


import com.google.common.collect.Multimap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shentong.spider.crawler.main.spider.SpiderConfigCrawlerService;
import shentong.spider.proxy.business.IProxyList;

import java.util.UUID;

/**
 * Created by Administrator on 2016/5/5.
 */
@Component
public class MaYiProxyList implements IProxyList {


    @Autowired
    private  SpiderConfigCrawlerService spiderListStarter;

    @Override
    public void batchFetch(Multimap multimap) {
//        HttpUtil httpUtil = HttpUtil.getInstance();
//        try {
//            String content = "";
//            for (int i = 1; i < 1254; i++) {
//                content = httpUtil.doGetForString("http://www.mayidaili.com/free/" + i);
//                Html html = new Html(content);
//                List<String> ips = html.xpath("//div[@class=\"col-md-9\"]//tbody/tr/td[1]").all();
//                List<String> ports = html.xpath("//div[@class=\"col-md-9\"]//tbody/tr/td[2]").all();
//                List<Header> headers = Lists.newArrayList();
//                headers.add(new BasicHeader("Cookie","proxy_token=" + html.xpath("//script").regex("proxy_token=(.*?);")));
//                for (String port:ports) {
//                    HttpResponse response = httpUtil.doGet(port,null,headers);
//                    Files.write(EntityUtils.toByteArray(response.getEntity()),new File(""));
//                }
//            }
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
        spiderListStarter.batchFetch(UUID.randomUUID().toString(),multimap);
    }

}

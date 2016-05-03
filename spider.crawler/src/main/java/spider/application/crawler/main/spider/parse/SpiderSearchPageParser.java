package spider.application.crawler.main.spider.parse;

import com.google.common.collect.Multimap;
import spider.application.crawler.common.processor.BasePageParser;
import spider.application.crawler.model.CrawlerSearchResult;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.selector.Html;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_yuananyun on 2016/4/5.
 */
public class SpiderSearchPageParser extends BasePageParser {
    private static Map<String, String> personBlackRegxMap = new HashMap<>();
    static {
//        姓名
        personBlackRegxMap.put("UserName", "<p><strong>(.*?)</strong></p>");
        //        证件号码
        personBlackRegxMap.put("IDCard", "<p>证件号码：<strong><span class=\"shenhe_qingkuang_xuanzhong\">(.*?)</span></strong></p>");
//        手机
        personBlackRegxMap.put("Tel", "手机号：(.*?)&nbsp;");
//        QQ
        personBlackRegxMap.put("QQ", "QQ号码：(.*?)&nbsp;");
//        户籍
        personBlackRegxMap.put("Nationality", "户籍：(.*?)&nbsp;");
//工作单位
        personBlackRegxMap.put("WorkUnit", "学校或单位：(.*?)&nbsp;");
        //职业
        personBlackRegxMap.put("Career", "职业：(.*?)&nbsp;");
//        备注
        personBlackRegxMap.put("Remark", "<p>备注：<strong>(.*?)</strong></p>");
        personBlackRegxMap.put("LoanAmount", "<p>借款金额：<strong>(.*?)</strong></p>");
        personBlackRegxMap.put("OverduePrincipal", "<p>逾期本金：<strong>(.*?)</strong></p>");
        personBlackRegxMap.put("Status", "<p>状态：<strong>(.*?)</strong></p>");
        personBlackRegxMap.put("LendingPlat", "<p>平台：<strong>(.*?)</strong></p>");
        personBlackRegxMap.put("LeadingPlatUrl", "<a href=\"(.*?);");

    }

    @Override
    public CrawlerSearchResult resolve(Page page, Site site,Multimap target) {
        Multimap multimap = configREsolveToMapList(page,site,target);
        return new CrawlerSearchResult("p2pblack",multimap);
    }

}

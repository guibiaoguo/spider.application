package spider.application.crawler.model;

import com.google.common.collect.Multimap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;

import java.util.Random;

/**
 * Created by Administrator on 2015/11/17.
 */
public class UcsmySite extends Site {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private int sleepRandom;

    public int getSleepRandom() {
        return sleepRandom;
    }

    public UcsmySite setSleepRandom(int sleepRandom) {
        this.sleepRandom = sleepRandom;
        return this;
    }

    @Override
    public int getSleepTime() {
        if(getSleepRandom() == 1) {
            Random random = new Random();
            int num = random.nextInt(10) + 1;
            logger.info("睡眠"+super.getSleepTime()*num/1000.0+"秒再开始工作！");
            return super.getSleepTime()*num;
        }
        return super.getSleepTime();
    }


}

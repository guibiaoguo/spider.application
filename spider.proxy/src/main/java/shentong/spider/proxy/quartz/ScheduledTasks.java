package shentong.spider.proxy.quartz;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shentong.spider.proxy.service.CrawlerProxyService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2015/12/29.
 */
@Component
@Configurable
@EnableScheduling
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private CrawlerProxyService crawlerProxyService;

    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentTime(){
        logger.info ("Scheduling Tasks Examples: The time is now " + dateFormat ().format (new Date ()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");
    }

    //每1分钟执行一次
    @Scheduled(fixedRate = 1000 * 30)
    public void reportCurrentByCron(){
        logger.info ("Scheduling Tasks releaseProxy By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
                    crawlerProxyService.releaseProxy();
//                    spiderDataService.insertData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");
    }

    //每1分钟执行一次
    @Scheduled(fixedRate = 1000 * 300 * 8)
    public void redisCurrentByCron(){
        logger.info ("Save Link By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
//                    spiderDataService.saveLink();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");

    }

    //每30分钟执行一次
//    @Scheduled(cron = "0 */30 * * * * ")
    @Scheduled(fixedRate = 1000 * 300 * 6)
    public void startjobCurrentByCron(){
        logger.info ("Start job By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
//                    spiderDataService.startJob();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");
    }

    //每30分钟执行一次
//    @Scheduled(cron = "0 */50 * * * * ")
    @Scheduled(fixedRate = 1000 * 300 * 4)
    public void stopjobCurrentByCron(){
        logger.info ("Stop job By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
//                    spiderDataService.stopJob();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");
    }

    @Scheduled(fixedRate = 1000 * 300 * 2)
    public void restarJobCurrentByCron(){
        logger.info ("restart Job By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
//                    spiderDataService.restartJob(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");

    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void reflashCookieCurrentByCron(){
        logger.info ("crawler Proxy By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
                    crawlerProxyService.batchFetch();
//                    spiderDataService.reflash();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");

    }

    @Scheduled(fixedRate = 1000 * 120)
    public void reflashProxyCurrentByCron(){
        logger.info ("reflash Proxy By Cron: The time is now " + dateFormat ().format (new Date()));
        logger.info("Start job");
        ExecutorService exec = Executors.newFixedThreadPool(1);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                logger.info("thread start");
                try {
//                    spiderDataService.reflashPxoy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logger.info("thread end");
            }
        });
        exec.execute(thread);
        exec.shutdown();
        while (!exec.isTerminated()) {
            // 等待所有子线程结束，才退出主线程
        }
        logger.info("end job");

    }

    private SimpleDateFormat dateFormat(){
        return new SimpleDateFormat ("YYYY-MM-dd HH:mm:ss");
    }

}

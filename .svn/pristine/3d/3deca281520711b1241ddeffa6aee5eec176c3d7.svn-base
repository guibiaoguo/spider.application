package spider.application.crawler.main;

import com.google.common.collect.Multimap;
import spider.application.crawler.business.IBackListSearchService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by ucs_yuananyun on 2016/4/6.
 */

public class SpiderListStarter {

    private List<IBackListSearchService> backListSearchList;
    private ExecutorService executorService;

    public void startCrawler(final String batchId, final Multimap keyList) {
        if (executorService == null || executorService.isShutdown())
            executorService = Executors.newFixedThreadPool(10);
        for (final IBackListSearchService backListSearch : backListSearchList) {
            //TODO delete it
//            if(!backListSearch.getClass().getName().contains("XIN")) continue;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    backListSearch.batchFetch(batchId,keyList);
                }
            });
        }

    }
}

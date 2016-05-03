package spider.application.crawler.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by ucs_yuananyun on 2016/4/6.
 */
public class CrawlerSearchResult {
    private String dataIdentifier;
    private Object data;
    private String batchId;
    private Collection<Map> sourceKeyList;

    public CrawlerSearchResult(String batchId, String dataIdentifier, Object data, Collection<Map> sourceKeyList) {
        this.dataIdentifier = dataIdentifier;
        this.data = data;
        this.batchId = batchId;
        this.sourceKeyList = sourceKeyList;
    }

    public CrawlerSearchResult(String dataIdentifier, Object data) {
        this.data = data;
        this.dataIdentifier = dataIdentifier;
    }

    public String getDataIdentifier() {
        return dataIdentifier;
    }

    public void setDataIdentifier(String dataIdentifier) {
        this.dataIdentifier = dataIdentifier;
    }

    public <T> T getData() {
        return (T)data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public Collection<Map> getSourceKeyList() {
        return sourceKeyList;
    }

    public void setSourceKeyList(Collection<Map> sourceKeyList) {
        this.sourceKeyList = sourceKeyList;
    }
}

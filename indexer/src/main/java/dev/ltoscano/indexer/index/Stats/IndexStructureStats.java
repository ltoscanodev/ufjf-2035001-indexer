package dev.ltoscano.indexer.index.stats;

import dev.ltoscano.indexer.configuration.AppConfig;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class IndexStructureStats 
{
    private final List<Long> insertTimeList;
    private final List<Long> memoryUsageList;
    
    public IndexStructureStats()
    {
        this.insertTimeList = new ArrayList<>();
        this.memoryUsageList = new ArrayList<>();
    }

    /**
     * @return the insertTimeList
     */
    public List<Long> getInsertTimeList() {
        return insertTimeList;
    }

    /**
     * @return the memoryUsageList
     */
    public List<Long> getMemoryUsageList() {
        return memoryUsageList;
    }
}

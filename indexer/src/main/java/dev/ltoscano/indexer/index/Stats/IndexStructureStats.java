package dev.ltoscano.indexer.index.Stats;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class IndexStructureStats 
{
    private final List<Long> insertionTimeList;
    private final List<Long> memoryUsageList;
    
    public IndexStructureStats()
    {
        this.insertionTimeList = new ArrayList<>();
        this.memoryUsageList = new ArrayList<>();
    }

    /**
     * @return the insertionTimeList
     */
    public List<Long> getInsertionTimeList() {
        return insertionTimeList;
    }

    /**
     * @return the memoryUsageList
     */
    public List<Long> getMemoryUsageList() {
        return memoryUsageList;
    }
}

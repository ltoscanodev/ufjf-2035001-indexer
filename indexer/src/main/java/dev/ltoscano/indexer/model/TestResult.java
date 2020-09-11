package dev.ltoscano.indexer.model;

import dev.ltoscano.indexer.index.Stats.IndexStats;
import dev.ltoscano.indexer.index.Stats.IndexStructureStats;
import dev.ltoscano.indexer.index.Stats.QueryStats;

/**
 *
 * @author ltosc
 */
public class TestResult
{
    private final int id;
    
    private final IndexStats indexStats;
    private final IndexStructureStats indexStructureStats;
    private final QueryStats queryStats;
    
    private final long bestTotalQueryTime;
    private final long avgTotalQueryTime;
    private final long worstTotalQueryTime;

    public TestResult() 
    {
        this(-1, new IndexStats(), new IndexStructureStats(), new QueryStats(), 0, 0, 0);
    }

    public TestResult(
            int id,
            IndexStats indexStats, IndexStructureStats indexStructureStats, QueryStats queryStats,
            long bestTotalQueryTime, long avgTotalQueryTime, long worstTotalQueryTime) 
    {
        this.id = id;
        
        this.indexStats = indexStats;
        this.indexStructureStats = indexStructureStats;
        this.queryStats = queryStats;
        
        this.bestTotalQueryTime = bestTotalQueryTime;
        this.avgTotalQueryTime = avgTotalQueryTime;
        this.worstTotalQueryTime = worstTotalQueryTime;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the indexStats
     */
    public IndexStats getIndexStats() {
        return indexStats;
    }

    /**
     * @return the indexStructureStats
     */
    public IndexStructureStats getIndexStructureStats() {
        return indexStructureStats;
    }

    /**
     * @return the queryStats
     */
    public QueryStats getQueryStats() {
        return queryStats;
    }

    /**
     * @return the bestTotalQueryTime
     */
    public long getBestTotalQueryTime() {
        return bestTotalQueryTime;
    }

    /**
     * @return the avgTotalQueryTime
     */
    public long getAvgTotalQueryTime() {
        return avgTotalQueryTime;
    }

    /**
     * @return the worstTotalQueryTime
     */
    public long getWorstTotalQueryTime() {
        return worstTotalQueryTime;
    }
}

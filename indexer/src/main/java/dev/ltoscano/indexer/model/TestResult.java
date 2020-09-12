package dev.ltoscano.indexer.model;

import dev.ltoscano.indexer.index.stats.IndexStats;
import dev.ltoscano.indexer.index.stats.IndexStructureStats;
import dev.ltoscano.indexer.index.stats.QueryStats;

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
    
    private final long randomOneWordTotalQueryTime;
    private final long randomTwoWordTotalQueryTime;
    
    private final long bestOneWordTotalQueryTime;
    private final long avgOneWordTotalQueryTime;
    private final long worstOneWordTotalQueryTime;
    
    private final long bestTwoWordTotalQueryTime;
    private final long avgTwoTotalQueryTime;
    private final long worstTwoTotalQueryTime;

    public TestResult() 
    {
        this(-1, new IndexStats(), new IndexStructureStats(), new QueryStats(), 0, 0, 0, 0, 0, 0, 0, 0);
    }

    public TestResult(
            int id,
            IndexStats indexStats, IndexStructureStats indexStructureStats, QueryStats queryStats,
            long randomOneWordTotalQueryTime, long randomTwoWordTotalQueryTime,
            long bestOneWordTotalQueryTime, long avgOneWordTotalQueryTime, long worstOneWordTotalQueryTime,
            long bestTwoWordTotalQueryTime, long avgTwoTotalQueryTime, long worstTwoTotalQueryTime) 
    {
        this.id = id;
        
        this.indexStats = indexStats;
        this.indexStructureStats = indexStructureStats;
        this.queryStats = queryStats;
        
        this.randomOneWordTotalQueryTime = randomOneWordTotalQueryTime;
        this.randomTwoWordTotalQueryTime = randomTwoWordTotalQueryTime;
        
        this.bestOneWordTotalQueryTime = bestOneWordTotalQueryTime;
        this.avgOneWordTotalQueryTime = avgOneWordTotalQueryTime;
        this.worstOneWordTotalQueryTime = worstOneWordTotalQueryTime;
        
        this.bestTwoWordTotalQueryTime = bestTwoWordTotalQueryTime;
        this.avgTwoTotalQueryTime = avgTwoTotalQueryTime;
        this.worstTwoTotalQueryTime = worstTwoTotalQueryTime;
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
     * @return the randomOneWordTotalQueryTime
     */
    public long getRandomOneWordTotalQueryTime() {
        return randomOneWordTotalQueryTime;
    }

    /**
     * @return the randomTwoWordTotalQueryTime
     */
    public long getRandomTwoWordTotalQueryTime() {
        return randomTwoWordTotalQueryTime;
    }

    /**
     * @return the bestOneWordTotalQueryTime
     */
    public long getBestOneWordTotalQueryTime() {
        return bestOneWordTotalQueryTime;
    }

    /**
     * @return the avgOneWordTotalQueryTime
     */
    public long getAvgOneWordTotalQueryTime() {
        return avgOneWordTotalQueryTime;
    }

    /**
     * @return the worstOneWordTotalQueryTime
     */
    public long getWorstOneWordTotalQueryTime() {
        return worstOneWordTotalQueryTime;
    }

    /**
     * @return the bestTwoWordTotalQueryTime
     */
    public long getBestTwoWordTotalQueryTime() {
        return bestTwoWordTotalQueryTime;
    }

    /**
     * @return the avgTwoTotalQueryTime
     */
    public long getAvgTwoTotalQueryTime() {
        return avgTwoTotalQueryTime;
    }

    /**
     * @return the worstTwoTotalQueryTime
     */
    public long getWorstTwoTotalQueryTime() {
        return worstTwoTotalQueryTime;
    }
}

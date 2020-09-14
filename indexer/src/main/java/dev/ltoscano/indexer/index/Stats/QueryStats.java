package dev.ltoscano.indexer.index.stats;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.model.QueryResult;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que armazena as estatísticas de uma consulta
 *
 * @author ltosc
 */
public class QueryStats
{
    private List<String> lastQuery;
    private long lastTotalQueryTime;
    private List<QueryResult> lastQueryResult;
    
    private int queryCount;
    
    private long chunkQueryTime;
    private long chunkGetTime;
    private long chunkRelevanceTime;
    private long chunkSortTime;
    
    private final List<Long> chunkQueryTimeList;
    private final List<Long> chunkGetTimeList;
    private final List<Long> chunkRelevanceTimeList;
    private final List<Long> chunkSortTimeList;
    
    public QueryStats()
    {
        this(new ArrayList<>(), 0, new ArrayList<>());
    }
    
    public QueryStats(List<String> query, long queryTime, List<QueryResult> queryResult)
    {
        this.lastQuery = query;
        this.lastTotalQueryTime = queryTime;
        this.lastQueryResult = queryResult;
        
        this.queryCount = 0;
        
        this.chunkQueryTime = 0;
        this.chunkGetTime = 0;
        this.chunkRelevanceTime = 0;
        this.chunkSortTime = 0;
        
        this.chunkQueryTimeList = new ArrayList<>();
        this.chunkGetTimeList = new ArrayList<>();
        this.chunkRelevanceTimeList = new ArrayList<>();
        this.chunkSortTimeList = new ArrayList<>();
    }

    /**
     * @return the lastQuery
     */
    public List<String> getLastQuery() {
        return lastQuery;
    }

    /**
     * @param lastQuery the lastQuery to set
     * @param lastQueryResult the lastQueryResult to set
     * @param lastQueryTime the lastTotalQueryTime
     */
    public void setLastQuery(List<String> lastQuery, List<QueryResult> lastQueryResult, long lastQueryTime, long getTime, long relevanceTime, long sortTime) 
    {
        this.lastQuery = lastQuery;
        this.lastTotalQueryTime = lastQueryTime;
        this.lastQueryResult = lastQueryResult;
        
        queryCount++;
        chunkQueryTime += lastQueryTime;
        chunkGetTime += getTime;
        chunkRelevanceTime += relevanceTime;
        chunkSortTime += sortTime;
        
        if((queryCount % AppConfig.queryChunk) == 0)
        {
            chunkQueryTimeList.add(chunkQueryTime);
            chunkGetTimeList.add(chunkGetTime);
            chunkRelevanceTimeList.add(chunkRelevanceTime);
            chunkSortTimeList.add(chunkSortTime);
            
            chunkQueryTime = 0;
            chunkGetTime = 0;
            chunkRelevanceTime = 0;
            chunkSortTime = 0;
        }
    }

    /**
     * @return the lastTotalQueryTime
     */
    public long getLastTotalQueryTime() {
        return lastTotalQueryTime;
    }

    /**
     * @return the lastQueryResult
     */
    public List<QueryResult> getLastQueryResult() {
        return lastQueryResult;
    }
    
    /**
     * @return the queryCount
     */
    public int getQueryCount() {
        return queryCount;
    }

    /**
     * @return the chunkQueryTimeList
     */
    public List<Long> getChunkQueryTimeList() {
        return chunkQueryTimeList;
    }

    /**
     * @return the chunkRelevanceTimeList
     */
    public List<Long> getChunkRelevanceTimeList() {
        return chunkRelevanceTimeList;
    }

    /**
     * @return the chunkSortTimeList
     */
    public List<Long> getChunkSortTimeList() {
        return chunkSortTimeList;
    }

    /**
     * @return the chunkGetTimeList
     */
    public List<Long> getChunkGetTimeList() {
        return chunkGetTimeList;
    }
}
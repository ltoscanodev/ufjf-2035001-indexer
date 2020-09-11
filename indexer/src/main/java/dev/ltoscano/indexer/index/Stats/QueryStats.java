package dev.ltoscano.indexer.index.Stats;

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
    // Lista de termos da consulta
    private List<String> lastQuery;
    // Tempo para obter o resultado da consulta (em nanosegundos)
    private long lastQueryTime;
    // Lista de resultados da consulta (Notícia, relevância)
    private List<QueryResult> lastQueryResult;
    
    private int queryCount;
    private long chunkQueryTime;
    private final List<Long> chunkQueryTimeList;
    
    public QueryStats()
    {
        this(new ArrayList<>(), 0, new ArrayList<>());
    }
    
    public QueryStats(List<String> query, long queryTime, List<QueryResult> queryResult)
    {
        this.lastQuery = query;
        this.lastQueryTime = queryTime;
        this.lastQueryResult = queryResult;
        
        this.queryCount = 0;
        this.chunkQueryTime = 0;
        this.chunkQueryTimeList = new ArrayList<>();
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
     * @param lastQueryTime the lastQueryTime
     */
    public void setLastQuery(List<String> lastQuery, List<QueryResult> lastQueryResult, long lastQueryTime) 
    {
        this.lastQuery = lastQuery;
        this.lastQueryTime = lastQueryTime;
        this.lastQueryResult = lastQueryResult;
        
        queryCount++;
        chunkQueryTime += lastQueryTime;
        
        if((queryCount % AppConfig.queryChunk) == 0)
        {
            chunkQueryTimeList.add(chunkQueryTime);
        }
    }

    /**
     * @return the lastQueryTime
     */
    public long getLastQueryTime() {
        return lastQueryTime;
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
}
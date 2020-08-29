package dev.ltoscano.indexer.index;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author ltosc
 */
public class IndexStats 
{
    private int datasetSize;
    private int indexEntries;
    
    private long buildTime;
    private long lastQueryTime;
    
    public IndexStats()
    {
        this.datasetSize = 0;
        this.indexEntries = 0;
        
        this.buildTime = 0;
        this.lastQueryTime = 0;
    }
    
    private long convertToSeconds(long time)
    {
        return TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
    }
    
    /**
     * @return the datasetSize
     */
    public int getDatasetSize() {
        return datasetSize;
    }

    /**
     * @param datasetSize the datasetSize to set
     */
    public void setDatasetSize(int datasetSize) {
        this.datasetSize = datasetSize;
    }

    /**
     * @return the indexEntries
     */
    public int getIndexEntries() {
        return indexEntries;
    }

    /**
     * @param indexEntries the indexEntries to set
     */
    public void setIndexEntries(int indexEntries) {
        this.indexEntries = indexEntries;
    }

    /**
     * @return the buildTime
     */
    public long getBuildTime()
    {
        return buildTime;
    }

    /**
     * @param buildTime the buildTime to set
     */
    public void setBuildTime(long buildTime) 
    {
        this.buildTime = convertToSeconds(buildTime);
    }

    /**
     * @return the lastQueryTime
     */
    public long getLastQueryTime() {
        return lastQueryTime;
    }

    /**
     * @param lastQueryTime the lastQueryTime to set
     */
    public void setLastQueryTime(long lastQueryTime) 
    {
        this.lastQueryTime = convertToSeconds(lastQueryTime);
    }
}

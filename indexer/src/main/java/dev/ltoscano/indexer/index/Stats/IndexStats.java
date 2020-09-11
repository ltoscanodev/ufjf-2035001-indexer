package dev.ltoscano.indexer.index.stats;

/**
 * Classe com as estatísticas do índice
 * 
 * @author ltosc
 */
public class IndexStats 
{
    // Quantidade de notícias no dataset
    private int datasetSize;
    // Quantidade de entradas no índice (Termos)
    private int indexEntries;
    // Tempo de construção (em nanosegundos) do índice
    private long buildTime;
    
    public IndexStats()
    {
        this.datasetSize = 0;
        this.indexEntries = 0;
        this.buildTime = 0;
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
        this.buildTime = buildTime;
    }
}

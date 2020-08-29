package dev.ltoscano.indexer.dataset;

import dev.ltoscano.indexer.configuration.AppConfig;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author ltosc
 */
public class FileDataset implements AutoCloseable
{
    private final BufferedReader bufferedReader;
    
     public FileDataset() throws FileNotFoundException
    {
        this.bufferedReader = new BufferedReader(new FileReader(AppConfig.datasetPath));
    }
    
    public FileDataset(String path) throws FileNotFoundException
    {
        this.bufferedReader = new BufferedReader(new FileReader(path));
    }
    
    public boolean hasNext() throws IOException
    {
        return bufferedReader.ready();
    }
    
    protected String getNextLine() throws IOException
    {
        return bufferedReader.readLine();
    }
    
    public void reset() throws IOException
    {
        bufferedReader.reset();
    }
    
    @Override
    public void close() throws IOException
    {
        bufferedReader.close();
    }
}

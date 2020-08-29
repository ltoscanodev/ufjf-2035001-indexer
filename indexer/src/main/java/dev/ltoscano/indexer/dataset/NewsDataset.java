package dev.ltoscano.indexer.dataset;

import com.google.gson.Gson;
import dev.ltoscano.indexer.model.News;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class NewsDataset extends FileDataset
{
    private final Gson jsonParser;
    
    public NewsDataset() throws FileNotFoundException
    {
        super();
        
        jsonParser = new Gson();
    }
    
    public NewsDataset(String path) throws FileNotFoundException
    {
        super(path);
        
        jsonParser = new Gson();
    }
    
    public News getNext() throws IOException
    {
        String newsLine = getNextLine();
        
        if(newsLine == null)
            throw new EOFException("End of dataset reached");
        
        News news = jsonParser.fromJson(newsLine, News.class);
        news.buildWordFrequencies();
        
        return news;
    }
    
    public List<News> getAll() throws IOException
    {
        List<News> newsList = new ArrayList<>();
        
        while(hasNext())
        {
            newsList.add(getNext());
        }
        
        return newsList;
    }
}

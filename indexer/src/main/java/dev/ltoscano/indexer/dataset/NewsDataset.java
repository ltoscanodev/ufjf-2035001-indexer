package dev.ltoscano.indexer.dataset;

import com.google.gson.Gson;
import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.model.News;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que faz a leitura do dataset de notícias (Arquivo JSON)
 *
 * @author ltosc
 */
public class NewsDataset implements AutoCloseable
{
    private final BufferedReader bufferedReader;
    private final Gson jsonParser;
    
    public NewsDataset() throws FileNotFoundException
    {
        this(AppConfig.datasetPath);
    }
    
    public NewsDataset(String path) throws FileNotFoundException
    {
        // Inicializa o BufferedReader com o arquivo do dataset configurado em 'AppConfig.datasetPath'
        this.bufferedReader = new BufferedReader(new FileReader(path));
        // Inicializa o parser de arquivos JSON
        this.jsonParser = new Gson();
    }
    
    public boolean hasNext() throws IOException
    {
        // Retorna o estado do buffer de leitura
        return bufferedReader.ready();
    }
    
    public News getNext() throws IOException
    {
        // Obtém a próxima linha do arquivo
        String newsLine = bufferedReader.readLine();
        
        // Verifica se chegou ao final do arquivo
        if(newsLine == null)
            // Se sim, lança exceção
            throw new EOFException("End of dataset reached");
        
        // Faz o parser do JSON para uma estrutura 'News'
        News news = jsonParser.fromJson(newsLine, News.class);
        
        // Constrói o mapa de palavras e suas frequências na notícia
        news.buildWordFrequencies();
        
        // Retorna a notícia
        return news;
    }
    
    public List<News> getAll() throws IOException
    {
        // Lista com todas as notícias do arquivo
        List<News> newsList = new ArrayList<>();
        
        // Faz a leitura de todas as notícias do arquivo
        while(hasNext())
        {
            newsList.add(getNext());
        }
        
        // Retorna a lista de todas as notícias
        return newsList;
    }
    
    public void reset() throws IOException
    {
        // Reseta o estado do buffer de leitura
        bufferedReader.reset();
    }
    
    @Override
    public void close() throws IOException
    {
        // Fecha o arquivo
        bufferedReader.close();
    }
}

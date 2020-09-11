package dev.ltoscano.indexer.model;

/**
 *  Classe que armazena o resultado de uma consulta ao índice
 * 
 * @author ltosc
 */
public class QueryResult 
{
    // Notícia do resultado
    private final News news;
    // Relevância da notícia
    private final Double relevance;
    
    public QueryResult(News news, Double relevance)
    {
        this.news = news;
        this.relevance = relevance;
    }

    /**
     * @return the news
     */
    public News getNews() {
        return news;
    }

    /**
     * @return the relevance
     */
    public Double getRelevance() {
        return relevance;
    }
}

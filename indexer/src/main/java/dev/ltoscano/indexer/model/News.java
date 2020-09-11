package dev.ltoscano.indexer.model;

import dev.ltoscano.indexer.token.Tokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  Classe que representa uma notícia obtida do dataset de notícias
 * 
 * @author ltosc
 */
public class News 
{
    // Categoria da notícia
    private String category;
    // Título da notícia
    private String headline;
    // Descrição da notícia
    private String short_description;
    // Autores da notícia
    private String authors;
    // Data da notícia
    private String date;
    // URL da notícia
    private String link;
    
    // Mapa de frequências de cada palavra da notícia
    private final Map<String, Integer> wordFrequencies;
    
    public News()
    {
        this("UNKNOWN", "No title", "No description.", "Unknown", "Unknown", "");
    }
    
    public News(String category, String headline, String shortDescription, String authors, String date, String link)
    {
        this.category = category;
        this.headline = headline;
        this.short_description = shortDescription;
        this.authors = authors;
        this.date = date;
        this.link = link;
        
        this.wordFrequencies = new HashMap<>();
    }
    
    /***
     * Função que preenche o mapa de frequências de cada palavra da notícia
     */
    public void buildWordFrequencies()
    {
        // Limpa o mapa
        wordFrequencies.clear();
        
        // Lista de palavras da notícia
        List<String> wordList = new ArrayList<>();
        // Faz o processo de tokenização do título da notícia
        wordList.addAll(Tokenizer.getTokens(getHeadline()));
        // Faz o processo de tokenização para a descrição da notícia
        wordList.addAll(Tokenizer.getTokens(getShortDescription()));
        
        // Para cada palavra da notícia
        for(String word : wordList)
        {
            // Verifica se o mapa ainda não contém a palavra
            if(!wordFrequencies.containsKey(word))
            {
                // Se não contiver, conta a frequência da palavra no documento e a armazena no mapa
                wordFrequencies.put(word, Collections.frequency(wordList, word));
            }
        }
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the headline
     */
    public String getHeadline() {
        return headline;
    }

    /**
     * @param headline the headline to set
     */
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * @return the short_description
     */
    public String getShortDescription()
    {
        return short_description;
    }

    /**
     * @param short_description the short_description to set
     */
    public void setShortDescription(String short_description) 
    {
        this.short_description = short_description;
        this.buildWordFrequencies();
    }

    /**
     * @return the authors
     */
    public String getAuthors() {
        return authors;
    }

    /**
     * @param authors the authors to set
     */
    public void setAuthors(String authors) {
        this.authors = authors;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the wordFrequencies
     */
    public Map<String, Integer> getWordFrequencies() {
        return wordFrequencies;
    }
}

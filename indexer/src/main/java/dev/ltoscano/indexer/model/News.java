package dev.ltoscano.indexer.model;

import dev.ltoscano.indexer.token.Tokenizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ltosc
 */
public class News 
{
    private static int ID_COUNT = 0;
    
    private final int id;
    private String category;
    private String headline;
    private String short_description;
    private String authors;
    private String date;
    private String link;
    
    private final Map<String, Integer> wordFrequencies;
    
    public News()
    {
        this("UNKNOWN", "No title", "No description.", "Unknown", "Unknown", "");
    }
    
    public News(String category, String headline, String shortDescription, String authors, String date, String link)
    {
        this.id = ID_COUNT++;
        this.category = category;
        this.headline = headline;
        this.short_description = shortDescription;
        this.authors = authors;
        this.date = date;
        this.link = link;
        
        this.wordFrequencies = new HashMap<>();
    }
    
    public void buildWordFrequencies()
    {
        wordFrequencies.clear();
        
        List<String> wordList = new ArrayList<>();
        wordList.addAll(Tokenizer.getTokens(getHeadline(), true));
        wordList.addAll(Tokenizer.getTokens(getShortDescription(), true));
        
        for(String word : wordList)
        {
            if(!wordFrequencies.containsKey(word))
            {
                wordFrequencies.put(word, Collections.frequency(wordList, word));
            }
        }
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
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

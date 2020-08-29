package dev.ltoscano.indexer.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class IndexEntry
{
    private final String word;
    private final List<Document> documentList;
    
    public IndexEntry(String word)
    {
        this.word = word;
        this.documentList = new ArrayList<>();
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }

    /**
     * @return the documentList
     */
    public List<Document> getDocumentList() {
        return documentList;
    }
}
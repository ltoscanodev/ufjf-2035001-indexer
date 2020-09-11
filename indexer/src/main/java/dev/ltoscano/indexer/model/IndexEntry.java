package dev.ltoscano.indexer.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa uma entrada na estrutura do índice
 *
 * @author ltosc
 */
public class IndexEntry
{
    // Palavra da entrada do índice
    private final String word;
    // Lista de documentos que contém esta palavra
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
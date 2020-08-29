package dev.ltoscano.indexer.index;

import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.dataset.NewsDataset;
import dev.ltoscano.indexer.exception.NotFoundException;
import dev.ltoscano.indexer.model.Document;
import dev.ltoscano.indexer.model.News;
import dev.ltoscano.indexer.structure.HashTable.CustomHashTable;
import dev.ltoscano.indexer.structure.List.DoubleLinkedList;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.structure.List.SimpleLinkedList;

/**
 *
 * @author ltosc
 */
public class Index
{
    private final String datasetPath;
    private final List<News> newsList;
    
    private final IndexStats indexStats;
    private final IndexStructure index;
    
    public Index(String datasetPath) throws IOException
    {
        this.datasetPath = datasetPath;
        this.newsList = new ArrayList<>();
        
        this.indexStats = new IndexStats();
        
        switch(AppConfig.indexStructureType)
        {
            case SimpleLinkedList:
            {
                this.index = new SimpleLinkedList();
                break;
            }
            case DoubleLinkedList:
            {
                this.index = new DoubleLinkedList();
                break;
            }
            case HashTable:
            {
                this.index = new CustomHashTable();
                break;
            }
            default:
            {
                this.index = new DoubleLinkedList();
            }
        }
        
        this.buildIndex();
    }
    
    private void buildIndex() throws IOException
    {
        long startTime = System.nanoTime();
        
        try(NewsDataset newsDataset = new NewsDataset(datasetPath))
        {
            int id = 0;
            
            while(newsDataset.hasNext())
            {
                News news = newsDataset.getNext();
                Map<String, Integer> wordFrequencies = news.getWordFrequencies();

                for(String word : wordFrequencies.keySet())
                {
                    if(!index.contains(word))
                    {
                        IndexEntry indexEntry = new IndexEntry(word);
                        indexEntry.getDocumentList().add(new Document(id));

                        index.insert(word, indexEntry);
                    }
                    else
                    {
                        index.get(word).getDocumentList().add(new Document(id));
                    }
                }
                
                newsList.add(news);
                id++;
            }
        }
        
        calculateDocumentWeights();
        
        indexStats.setDatasetSize(newsList.size());
        indexStats.setIndexEntries(index.size());
        indexStats.setBuildTime(System.nanoTime() - startTime);
    }
    
    private void calculateDocumentWeights()
    {
        int N = newsList.size();
        
        for(IndexEntry indexEntry : index.values())
        {
            int dj = indexEntry.getDocumentList().size();
            
            for(Document document : indexEntry.getDocumentList())
            {
                News news = newsList.get(document.getId());
                
                int fij;
                        
                try
                {
                    fij = news.getWordFrequencies().get(indexEntry.getWord());
                }
                catch(NullPointerException ex)
                {
                    fij = 0;
                }
                
                double log2N = (Math.log(N) / Math.log(2));
                
                double weight = fij * (log2N / dj);
                document.setWeight(weight);
            }
        }
    }
    
    public List<News> query(List<String> wordList)
    {
        long startTime = System.nanoTime();
        
        List<News> resultList = new ArrayList<>();
        
        Map<Integer, Double> relevanceMap = new HashMap<>();
        
        for(String word : wordList)
        {
            try
            {
                List<Document> wordDocList = index.get(word).getDocumentList();
            
                for(Document doc : wordDocList)
                {
                    double sumWeights = relevanceMap.containsKey(doc.getId()) ? 
                            relevanceMap.get(doc.getId()) : 0.0;

                    sumWeights += doc.getWeight();
                    relevanceMap.put(doc.getId(), sumWeights);
                }
            }
            catch(NotFoundException ex)
            {
                
            }
        }
        
        for(Integer key : relevanceMap.keySet())
        {
            double relevance = (1.0 / newsList.get(key).getWordFrequencies().size()) * relevanceMap.get(key);
            relevanceMap.put(key, relevance);
        }
        
        Map<Integer, Double> topTen
                = relevanceMap.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .limit(AppConfig.queryLimit)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        Iterator<Integer> keysIt = topTen.keySet().iterator();
        
        while(keysIt.hasNext())
        {
            resultList.add(newsList.get(keysIt.next()));
        }
        
        indexStats.setLastQueryTime(System.nanoTime() - startTime);
        
        return resultList;
    }

    /**
     * @return the newsList
     */
    public List<News> getNewsList() {
        return newsList;
    }

    /**
     * @return the index
     */
    public IndexStructure get() {
        return index;
    }

    /**
     * @return the indexStats
     */
    public IndexStats getIndexStats() {
        return indexStats;
    }
}

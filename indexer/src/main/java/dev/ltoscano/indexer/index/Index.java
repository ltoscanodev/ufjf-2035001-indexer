package dev.ltoscano.indexer.index;

import dev.ltoscano.indexer.index.stats.IndexStats;
import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.dataset.NewsDataset;
import dev.ltoscano.indexer.index.stats.IndexStructureStats;
import dev.ltoscano.indexer.index.stats.QueryStats;
import dev.ltoscano.indexer.model.Document;
import dev.ltoscano.indexer.model.News;
import dev.ltoscano.indexer.model.QueryResult;
import dev.ltoscano.indexer.sort.MergeSort;
import dev.ltoscano.indexer.structure.AVL.AVL;
import dev.ltoscano.indexer.structure.HashTable.HashTable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.structure.IndexStructure.IndexStructureType;
import dev.ltoscano.indexer.structure.Trie.Trie;
import dev.ltoscano.indexer.token.Tokenizer;
import java.util.Map.Entry;

/**
 * Índice de notícias
 *
 * @author ltosc
 */
public class Index
{
    private final IndexStructure indexStructure;
    
    private final IndexStats indexStats;
    private final QueryStats queryStats;
    
    private final List<News> newsList;
    
    public Index() throws IOException
    {
        this(AppConfig.datasetPath, AppConfig.indexStructureType);
    }
    
    public Index(String datasetPath, IndexStructureType indexStructureType) throws IOException
    {
        // Inicializa a lista de notícias
        this.newsList = new ArrayList<>();
        
        // Inicializa as estatísticas do índice
        this.indexStats = new IndexStats();
        this.queryStats = new QueryStats();
        
        // Define a estrutura de dados que será utilizada pelo índice
        switch(indexStructureType)
        {
            case HashTable:
            {
                // Tabela Hash
                this.indexStructure = new HashTable();
                break;
            }
            case AVL:
            {
                // Árvore AVL
                this.indexStructure = new AVL();
                break;
            }
            case Trie:
            {
                // Trie
                this.indexStructure = new Trie();
                break;
            }
            default:
            {
                // Por padrão, usar uma tabela hash
                this.indexStructure = new HashTable();
            }
        }
        
        // Constrói o índice de notícias
        this.buildIndex(datasetPath);
    }
    
    /**
     * Função que constrói o índice
     * 
     * @param datasetPath Caminho absoluto do arquivo de notícias
     * @throws IOException 
     */
    private void buildIndex(String datasetPath) throws IOException
    {
        // Marca o tempo inicial de construção do índice
        long startTime = System.nanoTime();
        
        // Cria o dataset de notícias
        try(NewsDataset newsDataset = new NewsDataset(datasetPath))
        {
            // Identificador de cada notícia
            int id = 0;
            
            // Enquanto houverem notícias no dataset
            while(newsDataset.hasNext())
            {
                // Obtém a próxima notícia
                News news = newsDataset.getNext();
                
                // Obtém o mapa de palavras da notícia e suas frequências
                Map<String, Integer> wordFrequencies = news.getWordFrequencies();

                // Para cada palavra no mapa
                for(String word : wordFrequencies.keySet())
                {
                    // Verifica se o índice ainda não contém a palavra
                    if(!indexStructure.contains(word))
                    {
                        // Se não contém, cria uma entrada de índice para a palavra
                        IndexEntry indexEntry = new IndexEntry(word);
                        
                        // Adiciona o documento (Notícia) à lista de documentos da entrada
                        indexEntry.getDocumentList().add(new Document(id));

                        // Insere a entrada no índice
                        indexStructure.insert(word, indexEntry);
                    }
                    else
                    {
                        // Se contém, obtém a entrada de índice e adciona à lista de documentos
                        indexStructure.get(word).getDocumentList().add(new Document(id));
                    }
                }
                
                // Armazena a notícia
                newsList.add(news);
                id++;
            }
        }
        
        // Pré-calcula os pesos dos termos em cada documento (IDF)
        calculateDocumentWeights();
        
        // Armazena as estatísticas do índice
        indexStats.setDatasetSize(newsList.size());
        indexStats.setIndexEntries(indexStructure.size());
        indexStats.setBuildTime(System.nanoTime() - startTime);
    }
    
    /***
     * Função que pré-calcula os pesos dos termos nos documentos (IDF)
     */
    private void calculateDocumentWeights()
    {
        // Número de documentos na coleção
        int N = newsList.size();
        
        // Para cada entrada do índice
        for(IndexEntry indexEntry : indexStructure.values())
        {
            // Número de documentos na coleção que contém o termo tj
            int dj = indexEntry.getDocumentList().size();
            
            // Para cada documento da entrada do índice
            for(Document document : indexEntry.getDocumentList())
            {
                // Obtém a notícia a partir do id do documento
                News news = newsList.get(document.getId());
                
                // Número de ocorrências do termo tj no documento i
                int fij = news.getWordFrequencies().get(indexEntry.getWord());
                // Logarítmo na base 2 de N
                double log2N = (Math.log(N) / Math.log(2));
                // Peso do termo tj no documento i
                double weight = fij * (log2N / dj);
                
                // Armazena o peso calculado do termo tj para o documento i
                document.setWeight(weight);
            }
        }
    }
    
    /***
     * Função que faz uma consulta no índice de notícias
     * 
     * @param wordList Lista de termos da consulta
     * @return Lista de resultados para a consulta
     */
    public List<QueryResult> query(List<String> wordList)
    {
        // Tempo inicial da consulta
        long startTime = System.nanoTime();
        
        // Mapa de identificadores e valores de relevância de cada documento
        Map<Integer, Double> relevanceMap = new HashMap<>();
        
        // Para cada termo da consulta
        for(String word : wordList)
        {
            // Processa a palavra a obtém a entrada de índice correspondente
            IndexEntry indexEntry = indexStructure.get(Tokenizer.normalize(word));
            
            // Verifica se a entrada de índice foi encontrada
            if(indexEntry != null)
            {
                // Se sim, obtém a lista de documentos que contém o termo
                List<Document> wordDocList = indexEntry.getDocumentList();
            
                // Para cada documento da lista, soma os pesos pré-calculados
                // caso eles existam no documento
                for(Document doc : wordDocList)
                {
                    // Obtém a relevância do documento no mapa de relevâncias
                    double relevance = 
                            relevanceMap.containsKey(doc.getId()) ? 
                            relevanceMap.get(doc.getId()) : 0.0;

                    // Incrementa com o peso do documento para o termo
                    relevance += doc.getWeight();
                    
                    // Armazena novamente no mapa de relevância
                    relevanceMap.put(doc.getId(), relevance);
                }
            }
        }
        
        // Para cada documento no mapa de relevâncias
        for(Integer key : relevanceMap.keySet())
        {
            // Calcula a relevância do documento: r(i) = (1/ni) * sum(wij)
            relevanceMap.put(
                    key, 
                    (1.0 / newsList.get(key).getWordFrequencies().size()) * relevanceMap.get(key));
        }
        
        Entry<Integer, Double>[] relevanceList = MergeSort.sortMap(relevanceMap);
        
        List<QueryResult> resultList = new ArrayList<>();
        
        for(int i = 0; (i < relevanceList.length) && (i < AppConfig.queryLimit); i++)
        {
            resultList.add(new QueryResult(newsList.get(relevanceList[i].getKey()), relevanceList[i].getValue()));
        }
        
        // Armazena as estatísticas da consulta
        queryStats.setLastQuery(wordList,resultList, (System.nanoTime() - startTime));
        
        // Retorna o resultado
        return resultList;
    }

    /**
     * @return the indexStructure
     */
    public IndexStructure getIndexStructure() {
        return indexStructure;
    }

    /**
     * @return the indexStats
     */
    public IndexStats getIndexStats() {
        return indexStats;
    }
    
    /**
     * @return the indexStructureStats
     */
    public IndexStructureStats getIndexStructureStats(){
        return getIndexStructure().getStructureStats();
    }

    /**
     * @return the queryStats
     */
    public QueryStats getQueryStats() {
        return queryStats;
    }
}

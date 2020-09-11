package dev.ltoscano.indexer.configuration;

import dev.ltoscano.indexer.structure.IndexStructure.IndexStructureType;

/**
 * Classe que possui configurações da aplicação
 * 
 * @author ltosc
 */
public class AppConfig 
{
    public enum RunMode 
    {
        Web(0), Console(1), Test(2);
        
        private final int value;

        private RunMode(int value) 
        {
            this.value = value;
        }
        public int getValue() 
        {
            return value;
        }
    }
    
    // Define o modo que a aplicação irá rodar (Web, console ou testes)
    public static RunMode runMode = RunMode.Web;
    
    // Caminho para o dataset de notícias (O caminho deve ser absoluto)
    public static String datasetPath = "src/main/resources/dataset/dataset.json";
    // Estrutura de dados padrão para o índice de notícias
    public static IndexStructureType indexStructureType = IndexStructureType.HashTable;
    // Limite de resultados que devem ser retornados na busca
    public static int queryLimit = 20;
    
    // Caminho dos arquivos com consultas para melhor caso, caso médio e pior caso
    public static String bestTestFilePath = "src/main/resources/dataset/best-one-word-query.txt";
    public static String avgTestFilePath = "src/main/resources/dataset/avg-one-word-query.txt";
    public static String worstTestFilePath = "src/main/resources/dataset/worst-one-word-query.txt";
    
    // Quantidade de testes que serão executados em cada etapa
    public static int testRunSteps = 5;
    
    public static int memoryLogTime = 20000;
    public static int insertionLogTime = 20000;
    public static int queryChunk = 1000;
}

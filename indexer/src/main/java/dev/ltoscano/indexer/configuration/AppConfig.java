package dev.ltoscano.indexer.configuration;

import dev.ltoscano.indexer.structure.IndexStructure.IndexStructureType;

/**
 *
 * @author ltosc
 */
public class AppConfig 
{
    public static boolean runConsole = false;
    
    public static String datasetPath = "src/main/resources/dataset/dataset.json";
    public static IndexStructureType indexStructureType = IndexStructureType.HashTable;
    public static int queryLimit = 20;
}

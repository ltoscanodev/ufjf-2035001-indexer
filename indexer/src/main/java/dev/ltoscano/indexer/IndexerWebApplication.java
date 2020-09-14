package dev.ltoscano.indexer;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.structure.IndexStructure.IndexStructureType;
import org.springframework.boot.SpringApplication;

/**
 *
 * @author ltosc
 */
public class IndexerWebApplication 
{
    private static void setIndexStructureType(int type)
    {
        switch(type)
        {
            case 0:
            {
                AppConfig.indexStructureType = IndexStructureType.HashTable;
                break;
            }
            case 1:
            {
                AppConfig.indexStructureType = IndexStructureType.AVL;
                break;
            }
            case 2:
            {
                AppConfig.indexStructureType = IndexStructureType.Trie;
                break;
            }
            default:
            {
                AppConfig.indexStructureType = IndexStructureType.HashTable;
            }
        }
    }
    
    private static void parseArgs(String[] args)
    {
        switch(args.length)
        {
            case 2:
            {
                AppConfig.datasetPath = args[1];
                break;
            }
            case 3:
            {
                AppConfig.datasetPath = args[1];
                setIndexStructureType(Integer.valueOf(args[2]));
                break;
            }
            case 4:
            {
                AppConfig.datasetPath = args[1];
                setIndexStructureType(Integer.valueOf(args[2]));
                AppConfig.queryLimit = Integer.valueOf(args[3]);
                break;
            }
        }
    }
    
    private static void runApp(String[] args)
    {
        SpringApplication.run(IndexerApplication.class, args);
    }
    
    public static void main(String[] args)
    {
        parseArgs(args);
        runApp(args);
    }
}

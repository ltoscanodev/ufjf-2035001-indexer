package dev.ltoscano.indexer;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.structure.IndexStructure.IndexStructureType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndexerApplication 
{
    private static void setIndexStructureType(int type)
    {
        switch(type)
        {
            case 0:
            {
                AppConfig.indexStructureType = IndexStructureType.SimpleLinkedList;
                break;
            }
            case 1:
            {
                AppConfig.indexStructureType = IndexStructureType.DoubleLinkedList;
                break;
            }
            case 2:
            {
                AppConfig.indexStructureType = IndexStructureType.SkipList;
                break;
            }
            case 3:
            {
                AppConfig.indexStructureType = IndexStructureType.HashTable;
                break;
            }
            case 4:
            {
                AppConfig.indexStructureType = IndexStructureType.AVL;
                break;
            }
            case 5:
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
    
    public static void main(String[] args) 
    {
        switch(args.length)
        {
            case 1:
            {
                AppConfig.runConsole = (Integer.valueOf(args[0]) != 0);
                break;
            }
            case 2:
            {
                AppConfig.runConsole = (Integer.valueOf(args[0]) != 0);
                AppConfig.datasetPath = args[1];
                break;
            }
            case 3:
            {
                AppConfig.runConsole = (Integer.valueOf(args[0]) != 0);
                AppConfig.datasetPath = args[1];
                setIndexStructureType(Integer.valueOf(args[2]));
                break;
            }
            case 4:
            {
                AppConfig.runConsole = (Integer.valueOf(args[0]) != 0);
                AppConfig.datasetPath = args[1];
                setIndexStructureType(Integer.valueOf(args[2]));
                AppConfig.queryLimit = Integer.valueOf(args[3]);
                break;
            }
        }
        
        if(AppConfig.runConsole)
        {
            IndexerConsoleApplication.main(args);
        }
        else
        {
            SpringApplication.run(IndexerApplication.class, args);
        }
    }
}

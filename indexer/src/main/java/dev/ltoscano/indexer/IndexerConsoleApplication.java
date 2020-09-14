package dev.ltoscano.indexer;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.index.Index;
import dev.ltoscano.indexer.index.stats.IndexStats;
import dev.ltoscano.indexer.index.stats.QueryStats;
import dev.ltoscano.indexer.model.QueryResult;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.util.TimeUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ltosc
 */
public class IndexerConsoleApplication
{   
    private static Index index = null;
    
    private static int showMenu()
    {
        System.out.println();
        System.out.println("========== MENU ==========");
        System.out.println();
        
        System.out.println("Escolha uma opção:");
        System.out.println("[1] - Criar índice");
        System.out.println("[2] - Estatísticas do índice");
        System.out.println("[3] - Fazer consulta no índice");
        System.out.println("[0] - Sair");
        System.out.println("Opção: ");
        
        int option = new Scanner(System.in).nextInt();
        
        return option;
    }
    
    private static void buildIndex()
    {
        System.out.println();
        System.out.println("========== Criação do índice ==========");
        System.out.println();
        
        if(index != null)
        {
            System.out.println("O índice já foi criado");
        }
        else
        {
            try 
            {
                System.out.println("Aguarde, criando índice...");
                index = new Index();
                System.out.println("O índice foi criado em " + TimeUtil.convertNanoToSeconds(index.getIndexStats().getBuildTime()) + " segundos");
            }
            catch (IOException ex)
            {
                Logger.getLogger(IndexerConsoleApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private static void printIndexStats()
    {
        System.out.println();
        System.out.println("========== Estatísticas do índice ==========");
        System.out.println();
        
        if(index == null)
        {
            System.out.println("O índice ainda não foi criado");
        }
        else
        {
            IndexStats indexStats = index.getIndexStats();

            System.out.println("Tamanho do dataset: " + indexStats.getDatasetSize());
            System.out.println("Quantidade de entradas do índice: " + indexStats.getIndexEntries());
            System.out.println("Tempo da criação do índice (Em segundos): " + TimeUtil.convertNanoToSeconds(indexStats.getBuildTime()));
            
            QueryStats queryStats = index.getQueryStats();
                
            System.out.println();
            System.out.println("========== Estatísticas da última consulta ==========");

            System.out.print("Consulta: ");

            for(String word : queryStats.getLastQuery())
            {
                System.out.print(word + " ");
            }
            System.out.println();

            System.out.println("Tempo da consulta (Em milisegundos): " + TimeUtil.convertNanoToMilliseconds(queryStats.getLastTotalQueryTime()));

            System.out.println();
            System.out.println("Resultado:");

            for(QueryResult result : queryStats.getLastQueryResult())
            {
                System.out.println(
                        "[" + result.getRelevance() + "] ===> " 
                                + result.getNews().getHeadline());
            }
        }
    }
    
    private static void runQuery()
    {
        System.out.println();
        System.out.println("========== Consultar índice ==========");
        System.out.println();
        
        if(index == null)
        {
            System.out.println("O índice ainda não foi criado");
        }
        else
        {
            System.out.println("Insira os termos para consulta (Separados por espaços): ");

            String query = new Scanner(System.in).nextLine();
            
            if(query.isEmpty())
            {
                System.out.println("A consulta não possui termos");
            }
            else
            {
                List<QueryResult> resultList = index.query(Arrays.asList(query.split(" ")));

                for(QueryResult result : resultList)
                {
                    System.out.println();
                    System.out.println(result.getNews().getHeadline());
                    System.out.println(result.getNews().getShortDescription());
                    System.out.println(result.getNews().getAuthors() + " (" + result.getNews().getDate() + ")");
                }
            }
        }
    }
    
    private static void setIndexStructureType(int type)
    {
        switch(type)
        {
            case 0:
            {
                AppConfig.indexStructureType = IndexStructure.IndexStructureType.HashTable;
                break;
            }
            case 1:
            {
                AppConfig.indexStructureType = IndexStructure.IndexStructureType.AVL;
                break;
            }
            case 2:
            {
                AppConfig.indexStructureType = IndexStructure.IndexStructureType.Trie;
                break;
            }
            default:
            {
                AppConfig.indexStructureType = IndexStructure.IndexStructureType.HashTable;
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
        int option;
        
        while((option = showMenu()) != 0)
        {
            switch(option)
            {
                case 1:
                {
                    buildIndex();
                    break;
                }
                case 2:
                {
                    printIndexStats();
                    break;
                }
                case 3:
                {
                    runQuery();
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args)
    {
        parseArgs(args);
        runApp(args);
    }
}

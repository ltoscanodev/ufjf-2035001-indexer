package dev.ltoscano.indexer;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.index.Index;
import dev.ltoscano.indexer.index.IndexStats;
import dev.ltoscano.indexer.model.News;
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
                index = new Index(AppConfig.datasetPath);
                System.out.println("O índice foi criado em " + index.getIndexStats().getBuildTime() + " segundos");
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
            IndexStats stats = index.getIndexStats();

            System.out.println("Tamanho do dataset: " + stats.getDatasetSize());
            System.out.println("Quantidade de entradas do índice: " + stats.getIndexEntries());
            System.out.println("Tempo da criação do índice (em segundos): " + stats.getBuildTime());
            System.out.println("Tempo da último consulta (em segundos): " + stats.getLastQueryTime());
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
            
            if(query.isBlank())
            {
                System.out.println("A consulta não possui termos");
            }
            else
            {
                List<News> resultList = index.query(Arrays.asList(query.split(" ")));
                News news;

                for(int i = 0; i < resultList.size(); i++)
                {
                    news = resultList.get(i);

                    System.out.println();
                    System.out.println(news.getHeadline());
                    System.out.println(news.getShortDescription());
                    System.out.println(news.getAuthors() + ", " + news.getDate());
                }
            }
        }
    }
    
    public static void main(String[] args)
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
}

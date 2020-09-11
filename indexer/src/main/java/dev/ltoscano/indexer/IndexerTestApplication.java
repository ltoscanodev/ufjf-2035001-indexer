package dev.ltoscano.indexer;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.index.Index;
import dev.ltoscano.indexer.model.TestResult;
import dev.ltoscano.indexer.structure.IndexStructure.IndexStructureType;
import dev.ltoscano.indexer.util.TimeUtil;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ltosc
 */
public class IndexerTestApplication 
{
    private static void writeResult(BufferedWriter resultWriter, List<TestResult> testResultList) throws IOException
    {
        resultWriter.write("Step;Tamanho do dataset; Qtd. entradas do índice;Tempo de construção do índice(seg);Melhor tempo de consulta(ms);Tempo médio de consulta(ms);Pior tempo de consulta(ms);");
        resultWriter.newLine();
        
        for(TestResult testResult : testResultList)
        {
            resultWriter.write(testResult.getId() + ";");
            resultWriter.write(testResult.getIndexStats().getDatasetSize() + ";");
            resultWriter.write(testResult.getIndexStats().getIndexEntries() + ";");
            resultWriter.write(TimeUtil.convertNanoToSeconds(testResult.getIndexStats().getBuildTime()) + ";");
            resultWriter.write(testResult.getBestTotalQueryTime() + ";");
            resultWriter.write(testResult.getAvgTotalQueryTime() + ";");
            resultWriter.write(testResult.getWorstTotalQueryTime() + ";");
            resultWriter.newLine();
        }
        
        for(TestResult testResult : testResultList)
        {
            resultWriter.newLine();
            resultWriter.write("Step;Qtd. de entradas do índice;Uso de memória(bytes);");
            resultWriter.newLine();
        
            int logCount = (testResult.getIndexStats().getIndexEntries() / AppConfig.memoryLogTime);
            
            for(int i = 0; i < logCount; i++)
            {
                resultWriter.write(
                        testResult.getId() + ";" +
                        ((i + 1) * AppConfig.memoryLogTime) + ";" +
                        testResult.getIndexStructureStats().getMemoryUsageList().get(i) + ";");
                resultWriter.newLine();
            }
            
            resultWriter.newLine();
            resultWriter.write("Step;Qtd. de entradas do índice;Tempo de inserção(ns);");
            resultWriter.newLine();
            
            logCount = (testResult.getIndexStats().getIndexEntries() / AppConfig.insertionLogTime);
            
            for(int i = 0; i < logCount; i++)
            {
                resultWriter.write(
                        testResult.getId() + ";" +
                        ((i + 1) * AppConfig.insertionLogTime) + ";" +
                        testResult.getIndexStructureStats().getInsertionTimeList().get(i) + ";");
                resultWriter.newLine();
            }
            
            resultWriter.newLine();
            resultWriter.write("Chunk;Tempo consulta(ms);");
            resultWriter.newLine();
            
            List<Long> chunkQueryTimeList = testResult.getQueryStats().getChunkQueryTimeList();
            
            for(int i = 0; i < chunkQueryTimeList.size(); i++)
            {
                resultWriter.write(
                        testResult.getId() + ";" +
                        (i + 1) + ";" + 
                        TimeUtil.convertNanoToMilliseconds(chunkQueryTimeList.get(i)) + ";");
                resultWriter.newLine();
            }
        }
    }
    
    private static long runTestCase(String queryFilePath, Index index) throws FileNotFoundException, IOException
    {
        long totalQueryTime = 0;
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(queryFilePath))) 
        {
            while (bufferedReader.ready()) 
            {
                index.query(Arrays.asList(bufferedReader.readLine().split(" ")));
                totalQueryTime += index.getQueryStats().getLastQueryTime();
            }
        }
        
        return totalQueryTime;
    }
    
    private static TestResult runTestStep(int step) throws IOException
    {
        System.out.println("Building index...");

        Index index = new Index();
        long indexBuildTime = TimeUtil.convertNanoToSeconds(index.getIndexStats().getBuildTime());

        System.out.println("Index build time: " + indexBuildTime);

        System.out.println("Testing queries...");
        
        long bestTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(AppConfig.bestTestFilePath, index));
        long avgTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(AppConfig.avgTestFilePath, index));
        long worstTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(AppConfig.worstTestFilePath, index));
        
        System.out.println("Best total query time: " + bestTotalQueryTime);
        System.out.println("Avg. total query time: " + avgTotalQueryTime);
        System.out.println("Worst. total query time: " + worstTotalQueryTime);

        return new TestResult(
                step,
                index.getIndexStats(), index.getIndexStructureStats(), index.getQueryStats(),
                bestTotalQueryTime, avgTotalQueryTime, worstTotalQueryTime);
    }
    
    private static void runTest(String resultFilePath, IndexStructureType indexStructureType) throws IOException
    {
        AppConfig.indexStructureType = indexStructureType;

        List<TestResult> testResultList = new ArrayList<>();

        for (int i = 0; i < AppConfig.testRunSteps; i++) 
        {
            System.out.println("Step (" + i + "):");
            testResultList.add(runTestStep(i));
            System.out.println();
        }
        
        try(BufferedWriter resultWriter = new BufferedWriter(new FileWriter(resultFilePath)))
        {
            writeResult(resultWriter, testResultList);
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
                AppConfig.bestTestFilePath = args[2];
                break;
            }
            case 4:
            {
                AppConfig.datasetPath = args[1];
                AppConfig.bestTestFilePath = args[2];
                AppConfig.avgTestFilePath = args[3];
                break;
            }
            case 5:
            {
                AppConfig.datasetPath = args[1];
                AppConfig.bestTestFilePath = args[2];
                AppConfig.avgTestFilePath = args[3];
                AppConfig.worstTestFilePath = args[4];
                break;
            }
            case 6:
            {
                AppConfig.datasetPath = args[1];
                AppConfig.bestTestFilePath = args[2];
                AppConfig.avgTestFilePath = args[3];
                AppConfig.worstTestFilePath = args[4];
                AppConfig.testRunSteps = Integer.valueOf(args[5]);
                break;
            }
        }
    }
    
    private static void runApp(String[] args)
    {
        try
        {
            File hashTableResultDirPath = new File("Results/" + + IndexStructureType.HashTable.getValue());
            
            if(!hashTableResultDirPath.exists())
            {
                hashTableResultDirPath.mkdirs();
            }
            
            System.out.println("===== HashTable Test =====");
            runTest(hashTableResultDirPath + "/result-one-hashtable.csv", IndexStructureType.HashTable);
            System.out.println();
            
            File avlResultDirPath = new File("Results/" + IndexStructureType.AVL.getValue());
            
            if(!avlResultDirPath.exists())
            {
                avlResultDirPath.mkdirs();
            }
            
            System.out.println("===== AVL Test =====");
            runTest(avlResultDirPath + "/result-one-avl.csv", IndexStructureType.AVL);
            System.out.println();
            
            File trieResultDirPath = new File("Results/" + IndexStructureType.Trie.getValue());
            
            if(!trieResultDirPath.exists())
            {
                trieResultDirPath.mkdirs();
            }
            
            System.out.println("===== Trie Test =====");
            runTest(trieResultDirPath + "/result-one-trie.csv", IndexStructureType.Trie);
            System.out.println();
        } 
        catch (IOException ex)
        {
            Logger.getLogger(IndexerTestApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args)
    {
        parseArgs(args);
        runApp(args);
    }
}

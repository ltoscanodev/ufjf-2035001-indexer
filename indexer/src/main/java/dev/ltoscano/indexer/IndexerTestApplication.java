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
    private static List<String> randomOneWordQueryList;
    private static List<String> randomTwoWordQueryList;
    private static List<String> bestOneWordQueryList;
    private static List<String> avgOneWordQueryList;
    private static List<String> worstOneWordQueryList;
    private static List<String> bestTwoWordQueryList;
    private static List<String> avgTwoWordQueryList;
    private static List<String> worstTwoWordQueryList;
    
    private static void writeResult(BufferedWriter resultWriter, List<TestResult> testResultList) throws IOException
    {
        resultWriter.write(
                "Step;"
                + "Tamanho do dataset; "
                + "Qtd. entradas do índice;"
                + "Tempo de construção do índice(seg);"
                + "Tempo de consulta para 1 palavra - Aleatório(ms);"
                + "Tempo de consulta para 2 palavras - Aleatório(ms);"
                + "Tempo de consulta para 1 palavra - Melhor caso(ms);"
                + "Tempo de consulta para 1 palavra - Caso médio(ms);"
                + "Tempo de consulta para 1 palavra - Pior caso(ms);"
                + "Tempo de consulta para 2 palavras - Melhor caso(ms);"
                + "Tempo de consulta para 2 palavras - Caso médio(ms);"
                + "Tempo de consulta para 2 palavras - Pior caso(ms);");
        resultWriter.newLine();
        
        for(TestResult testResult : testResultList)
        {
            resultWriter.write(testResult.getId() + ";");
            resultWriter.write(testResult.getIndexStats().getDatasetSize() + ";");
            resultWriter.write(testResult.getIndexStats().getIndexEntries() + ";");
            resultWriter.write(testResult.getIndexStats().getBuildTime() + ";");
            resultWriter.write(testResult.getRandomOneWordTotalQueryTime() + ";");
            resultWriter.write(testResult.getRandomTwoWordTotalQueryTime() + ";");
            resultWriter.write(testResult.getBestOneWordTotalQueryTime() + ";");
            resultWriter.write(testResult.getAvgOneWordTotalQueryTime() + ";");
            resultWriter.write(testResult.getWorstOneWordTotalQueryTime() + ";");
            resultWriter.write(testResult.getBestTwoWordTotalQueryTime() + ";");
            resultWriter.write(testResult.getAvgTwoTotalQueryTime() + ";");
            resultWriter.write(testResult.getWorstTwoTotalQueryTime()+ ";");
            resultWriter.newLine();
        }
        
        resultWriter.newLine();
        resultWriter.write("Step;Qtd. de entradas do índice;Tempo de inserção(ns);");
        resultWriter.newLine();
        
        for(TestResult testResult : testResultList)
        {
            List<Long> insertTimeList = testResult.getIndexStructureStats().getInsertTimeList();
            
            for(int i = 0; i < insertTimeList.size(); i++)
            {
                resultWriter.write(
                        testResult.getId() + ";" +
                        ((i + 1) * AppConfig.insertLogTime) + ";" +
                        insertTimeList.get(i) + ";");
                resultWriter.newLine();
            }
        }
        
        resultWriter.newLine();
        resultWriter.write("Step;Qtd. de entradas do índice;Uso de memória(bytes);");
        resultWriter.newLine();
        
        for(TestResult testResult : testResultList)
        {
            List<Long> memoryUsageList = testResult.getIndexStructureStats().getMemoryUsageList();
            
            for(int i = 0; i < memoryUsageList.size(); i++)
            {
                resultWriter.write(
                        testResult.getId() + ";" +
                        ((i + 1) * AppConfig.memoryLogTime) + ";" +
                        memoryUsageList.get(i) + ";");
                resultWriter.newLine();
            }
        }
        
        resultWriter.newLine();
        resultWriter.write("Step;Chunk;Tempo consulta(ms);Tempo de busca no índice(ns);Tempo do cálculo de relevância(ms);Tempo da ordenação do resultado(ms);");
        resultWriter.newLine();
        
        for(TestResult testResult : testResultList)
        {
            List<Long> chunkQueryTimeList = testResult.getQueryStats().getChunkQueryTimeList();
            List<Long> chunkGetTimeList = testResult.getQueryStats().getChunkGetTimeList();
            List<Long> chunkRelevanceTimeList = testResult.getQueryStats().getChunkRelevanceTimeList();
            List<Long> chunkSortTimeList = testResult.getQueryStats().getChunkSortTimeList();
            
            for(int i = 0; i < chunkQueryTimeList.size(); i++)
            {
                resultWriter.write(
                        testResult.getId() + ";" +
                        (i + 1) + ";" + 
                        TimeUtil.convertNanoToMilliseconds(chunkQueryTimeList.get(i)) + ";" +
                        chunkGetTimeList.get(i) + ";" +
                        TimeUtil.convertNanoToMilliseconds(chunkRelevanceTimeList.get(i)) + ";" +
                        TimeUtil.convertNanoToMilliseconds(chunkSortTimeList.get(i)) + ";");
                resultWriter.newLine();
            }
        }
    }
    
    private static long runTestCase(List<String> queryList, Index index) throws FileNotFoundException, IOException
    {
        long totalQueryTime = 0;
        
        for(int i = 0; i < queryList.size(); i++)
        {
            index.query(Arrays.asList(queryList.get(i).split(" ")));
            totalQueryTime += index.getQueryStats().getLastTotalQueryTime();
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
        
        long randomOneWordQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(randomOneWordQueryList, index));
        long randomTwoWordQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(randomTwoWordQueryList, index));
        long bestOneWordTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(bestOneWordQueryList, index));
        long avgOneWordTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(avgOneWordQueryList, index));
        long worstOneWordTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(worstOneWordQueryList, index));
        long bestTwoWordTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(bestTwoWordQueryList, index));
        long avgTwoWordTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(avgTwoWordQueryList, index));
        long worstTwoWordTotalQueryTime = TimeUtil.convertNanoToMilliseconds(runTestCase(worstTwoWordQueryList, index));
        
        System.out.println("One word total query time: " + randomOneWordQueryTime);
        System.out.println("Two word total query time: " + randomTwoWordQueryTime);
        System.out.println("Best total query time: " + bestOneWordTotalQueryTime);
        System.out.println("Avg. total query time: " + avgOneWordTotalQueryTime);
        System.out.println("Worst. total query time: " + worstOneWordTotalQueryTime);
        System.out.println("Best total query time: " + bestTwoWordTotalQueryTime);
        System.out.println("Avg. total query time: " + avgTwoWordTotalQueryTime);
        System.out.println("Worst. total query time: " + worstTwoWordTotalQueryTime);

        return new TestResult(
                step,
                index.getIndexStats(), index.getIndexStructureStats(), index.getQueryStats(),
                randomOneWordQueryTime, randomTwoWordQueryTime,
                bestOneWordTotalQueryTime, avgOneWordTotalQueryTime, worstOneWordTotalQueryTime,
                bestTwoWordTotalQueryTime, avgTwoWordTotalQueryTime, worstTwoWordTotalQueryTime);
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
            case 4:
            {
                AppConfig.datasetPath = args[1];
                AppConfig.randomOneWordTestFilePath = args[2];
                AppConfig.randomTwoWordTestFilePath = args[3];
                break;
            }
            case 7:
            {
                AppConfig.datasetPath = args[1];
                AppConfig.randomOneWordTestFilePath = args[2];
                AppConfig.randomTwoWordTestFilePath = args[3];
                AppConfig.bestOneWordTestFilePath = args[4];
                AppConfig.avgOneWordTestFilePath = args[5];
                AppConfig.worstOneWordTestFilePath = args[6];
                break;
            }
            case 10:
            {
                AppConfig.datasetPath = args[1];
                AppConfig.randomOneWordTestFilePath = args[2];
                AppConfig.randomTwoWordTestFilePath = args[3];
                AppConfig.bestOneWordTestFilePath = args[4];
                AppConfig.avgOneWordTestFilePath = args[5];
                AppConfig.worstOneWordTestFilePath = args[6];
                AppConfig.bestTwoWordTestFilePath = args[7];
                AppConfig.avgTwoWordTestFilePath = args[8];
                AppConfig.worstTwoWordTestFilePath = args[9];
                break;
            }
        }
    }
    
    private static List<String> loadQueryList(String queryFilePath) throws FileNotFoundException, IOException
    {
        List<String> queryList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(queryFilePath))) 
        {
            while (bufferedReader.ready()) 
            {
                queryList.add(bufferedReader.readLine());
            }
        }
        
        return queryList;
    }
    
    private static void runApp(String[] args)
    {
        try
        {
            File resultDirPath = new File("Results/");
            
            if(!resultDirPath.exists())
            {
                resultDirPath.mkdirs();
            }
            
            randomOneWordQueryList = loadQueryList(AppConfig.randomOneWordTestFilePath);
            randomTwoWordQueryList = loadQueryList(AppConfig.randomTwoWordTestFilePath);
            bestOneWordQueryList = loadQueryList(AppConfig.bestOneWordTestFilePath);
            avgOneWordQueryList = loadQueryList(AppConfig.avgOneWordTestFilePath);
            worstOneWordQueryList = loadQueryList(AppConfig.worstOneWordTestFilePath);
            bestTwoWordQueryList = loadQueryList(AppConfig.bestTwoWordTestFilePath);
            avgTwoWordQueryList = loadQueryList(AppConfig.avgTwoWordTestFilePath);
            worstTwoWordQueryList = loadQueryList(AppConfig.worstTwoWordTestFilePath);
            
            System.out.println("===== HashTable Test =====");
            runTest(resultDirPath + "/result-hashtable.csv", IndexStructureType.HashTable);
            System.out.println();

            System.out.println("===== AVL Test =====");
            runTest(resultDirPath + "/result-avl.csv", IndexStructureType.AVL);
            System.out.println();

            System.out.println("===== Trie Test =====");
            runTest(resultDirPath + "/result-trie.csv", IndexStructureType.Trie);
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

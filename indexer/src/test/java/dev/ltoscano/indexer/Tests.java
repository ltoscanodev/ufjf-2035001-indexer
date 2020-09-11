package dev.ltoscano.indexer;

import dev.ltoscano.indexer.token.Tokenizer;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ltosc
 */
public class Tests {

    private static void generateWords() 
    {
        List<String> keysList = new ArrayList<>();
        
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("keys.txt")))
        {
            while(bufferedReader.ready())
            {
                keysList.add(bufferedReader.readLine());
            }
        }
        catch (FileNotFoundException ex) 
        {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) 
        {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        List<String> loremList = new ArrayList<>();
//        
//        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("lorem.txt")))
//        {
//            while(bufferedReader.ready())
//            {
//                loremList.add(bufferedReader.readLine());
//            }
//        }
//        catch (FileNotFoundException ex) 
//        {
//            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//        } 
//        catch (IOException ex) 
//        {
//            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        List<String> wordList = new ArrayList<>();
        
        for(int i = 0; i < 10000; i++)
        {
            wordList.add(keysList.get(i));
            Collections.shuffle(keysList);
        }
        
//        for(int i = 0; i < 5000; i++)
//        {
//            wordList.add(loremList.get(i));
//            Collections.shuffle(loremList);
//        }
        
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("sucess-one-word-query.txt")))
        {
            Collections.shuffle(wordList);
            
            for(int i = 0; i < wordList.size(); i++)
            {
                bufferedWriter.write(wordList.get(i));
                bufferedWriter.newLine();
            }
        } 
        catch (IOException ex)
        {
            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        wordList.clear();
//        
//        for(int i = 0; i < 10000; i++)
//        {
//            wordList.add(keysList.get(i));
//            Collections.shuffle(keysList);
//        }
//        
//        for(int i = 0; i < 10000; i++)
//        {
//            wordList.add(loremList.get(i));
//        }
//        
//        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("two-word-query.txt")))
//        {
//            Collections.shuffle(wordList);
//            
//            for(int i = 0; i < wordList.size(); i += 2)
//            {
//                bufferedWriter.write(wordList.get(i) + " " + wordList.get(i + 1));
//                bufferedWriter.newLine();
//            }
//        } 
//        catch (IOException ex)
//        {
//            Logger.getLogger(Tests.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }   

    public static void main(String[] args) 
    {
        generateWords();
    }
}

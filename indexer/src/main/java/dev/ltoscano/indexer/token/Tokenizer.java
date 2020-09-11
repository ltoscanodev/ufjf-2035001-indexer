package dev.ltoscano.indexer.token;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 *
 * @author ltosc
 */
public class Tokenizer 
{
    public static String normalize(String str)
    {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }
    
    public static List<String> getTokens(String str)
    {
        return getTokens(str, " ");
    }
    
    public static List<String> getTokens(String str, String sep)
    {
        List<String> tokenList = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(str, sep);
        
        while(tokenizer.hasMoreTokens())
        {
            String token = tokenizer.nextToken();
            
            String normalizedToken = normalize(token);
                
            if(!normalizedToken.isEmpty())
            {
                tokenList.add(normalize(token));   
            }
        }
        
        return tokenList;
    }
}
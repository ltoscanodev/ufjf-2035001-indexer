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
    private static String normalizeString(String str)
    {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^A-Za-z0-9]", "").toLowerCase();
    }
    
    public static List<String> getTokens(String str, boolean normalize)
    {
        return getTokens(str, " ", normalize);
    }
    
    public static List<String> getTokens(String str, String sep, boolean normalize)
    {
        List<String> tokenList = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(str, sep);
        
        while(tokenizer.hasMoreTokens())
        {
            String token = tokenizer.nextToken();
            
            if(normalize)
            {
                String normalizedToken = normalizeString(token);
                
                if(!normalizedToken.isEmpty())
                {
                    tokenList.add(normalizeString(token));   
                }
            }
            else
            {
                tokenList.add(token);
            }
        }
        
        return tokenList;
    }
}
package dev.ltoscano.indexer.sort;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author ltosc
 */
public class MergeSort 
{
    private static Entry<Integer, Double>[] merge(Entry<Integer, Double>[] leftList, Entry<Integer, Double>[] rightList)
    {
        int i = 0;
        int j = 0;
        int k = 0;
        
        Entry<Integer, Double>[] mergeList = new Entry[leftList.length + rightList.length];
        
        while((i < leftList.length) && (j < rightList.length))
        {
            if(leftList[i].getValue() >= rightList[j].getValue())
            {
                mergeList[k++] = leftList[i++];
            }
            else
            {
                mergeList[k++] = rightList[j++];
            }
        }
        
        while(i < leftList.length)
        {
            mergeList[k++] = leftList[i++];
        }
        
        while(j < rightList.length)
        {
            mergeList[k++] = rightList[j++];
        }
        
        return mergeList;
    }
    
    private static Entry<Integer, Double>[] split(Entry<Integer, Double>[] entryList)
    {
        if(entryList.length < 2)
            return entryList;
        
        int middle = (entryList.length / 2);
        
        Entry<Integer, Double>[] leftList = new Entry[middle];
        
        for(int i = 0; i < middle; i++)
        {
            leftList[i] = entryList[i];
        }
        
        Entry<Integer, Double>[] rightList = new Entry[entryList.length - middle];
        
        for(int i = middle; i < entryList.length; i++)
        {
            rightList[i - middle] = entryList[i];
        }
        
        leftList = split(leftList);
        rightList = split(rightList);
        
        return merge(leftList, rightList);
    }
    
    public static Entry<Integer, Double>[] sortMap(Map<Integer, Double> map)
    {
        Entry<Integer, Double>[] entryList = new Entry[map.size()];
        int i = 0;
        
        for(Entry<Integer, Double> entry : map.entrySet())
        {
            entryList[i++] = entry;
        }
        
        return split(entryList);
    }
}

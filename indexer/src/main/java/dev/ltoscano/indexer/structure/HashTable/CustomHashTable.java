package dev.ltoscano.indexer.structure.HashTable;

import dev.ltoscano.indexer.model.IndexEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import dev.ltoscano.indexer.structure.IndexStructure;

/**
 *
 * @author ltosc
 */
public class CustomHashTable implements IndexStructure
{
    private final Map<String, IndexEntry> map;
    
    public CustomHashTable()
    {
        this.map = new HashMap<>();
    }
    
    @Override
    public List<String> keys() 
    {
        return new ArrayList(map.keySet());
    }

    @Override
    public List<IndexEntry> values() 
    {
        return new ArrayList(map.values());
    }

    @Override
    public void insert(String key, IndexEntry value)
    {
        map.put(key, value);
    }

    @Override
    public IndexEntry get(String key)
    {
        return map.get(key);
    }

    @Override
    public void remove(String key) 
    {
        map.remove(key);
    }

    @Override
    public boolean empty() 
    {
        return map.isEmpty();
    }

    @Override
    public boolean contains(String key) 
    {
        return map.containsKey(key);
    }

    @Override
    public int size() 
    {
        return map.size();
    }

    @Override
    public void print() 
    {
        
    }
}

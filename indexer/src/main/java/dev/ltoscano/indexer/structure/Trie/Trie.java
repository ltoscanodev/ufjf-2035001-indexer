package dev.ltoscano.indexer.structure.Trie;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.structure.Trie.Node.TrieNode;
import dev.ltoscano.indexer.util.MemoryUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class Trie extends IndexStructure
{
    private final TrieNode rootNode;
    
    private int itemCount;
    
    public Trie()
    {
        this.rootNode = new TrieNode();
        this.itemCount = 0;
    }
    
    private void listKeys(TrieNode node, String key, List<String> keys)
    {
        if(node == null)
            return;
        
        key += node.getKey();
        
        if(node.isKey())
            keys.add(key);
        
        TrieNode[] childens = node.getChildrenNodes();

        for(int i = 0; i < childens.length; i++)
        {
            if(childens[i] != null)
            {
                listKeys(childens[i], key, keys);
            }
        }
    }
    
    private void listValues(TrieNode node, List<IndexEntry> values)
    {
        if(node == null)
            return;
        
        if(node.isKey())
            values.add(node.getValue());
        
        TrieNode[] childens = node.getChildrenNodes();

        for(int i = 0; i < childens.length; i++)
        {
            if(childens[i] != null)
            {
                listValues(childens[i], values);
            }
        }
    }

    @Override
    public List<String> keys()
    {
        List<String> keyList = new ArrayList<>();
        listKeys(rootNode, "", keyList);
        
        return keyList;
    }

    @Override
    public List<IndexEntry> values() 
    {
        List<IndexEntry> valueList = new ArrayList<>();
        listValues(rootNode, valueList);
        
        return valueList;
    }

    @Override
    public void insert(String key, IndexEntry value)
    {
        long startTime = System.nanoTime();
        
        if(key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");
        
        TrieNode tmpNode = rootNode;
        
        for(int i = 0; i < key.length(); i++)
        {
            char keyChar = key.charAt(i);
            
            TrieNode childNode = tmpNode.getChild(keyChar);
            
            if(childNode == null)
            {
                childNode = new TrieNode(keyChar, tmpNode, null);
                
                tmpNode.setChild(keyChar, childNode);
                tmpNode = childNode;
            }
            else
            {
                tmpNode = childNode;
            }
        }
        
        tmpNode.setValue(value);
        itemCount++;
        
        if((itemCount % AppConfig.insertionLogTime) == 0)
        {
            getStructureStats().getInsertionTimeList().add(System.nanoTime() - startTime);
        }
        
        if((itemCount % AppConfig.memoryLogTime) == 0)
        {
            getStructureStats().getMemoryUsageList().add(MemoryUtil.getMemoryUsage());
        }
    }

    @Override
    public IndexEntry get(String key) 
    {
        if(key.isEmpty())
            throw new IllegalArgumentException("The key cannot be empty");
        
        TrieNode tmpNode = rootNode;
        
        for(int i = 0; i < key.length(); i++)
        {
            char keyChar = key.charAt(i);
            
            TrieNode childNode = tmpNode.getChild(keyChar);
            
            if(childNode == null)
            {
                return null;
            }
            else
            {
                tmpNode = childNode;
            }
        }
        
        return tmpNode.getValue();
    }

    @Override
    public boolean empty()
    {
        return (itemCount == 0);
    }

    @Override
    public boolean contains(String key) 
    {
        return (get(key) != null);
    }

    @Override
    public int size()
    {
        return itemCount;
    }
}

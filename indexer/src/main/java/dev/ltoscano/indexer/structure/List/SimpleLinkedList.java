package dev.ltoscano.indexer.structure.List;

import dev.ltoscano.indexer.exception.NotFoundException;
import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.structure.List.Node.SimpleNodeList;
import java.util.ArrayList;
import java.util.List;
import dev.ltoscano.indexer.structure.IndexStructure;

/**
 *
 * @author ltosc
 */
public class SimpleLinkedList implements IndexStructure
{
    private SimpleNodeList firstNode;
    private SimpleNodeList lastNode;
    private int listSize;
    
    public SimpleLinkedList()
    {
        this.firstNode = null;
        this.lastNode = null;
        this.listSize = 0;
    }
    
    @Override
    public List<String> keys()
    {
        List<String> keyList = new ArrayList();
        
        SimpleNodeList tmpNode = firstNode;
        
        while(tmpNode != null)
        {
            keyList.add(tmpNode.getKey());
            tmpNode = tmpNode.getNextNode();
        }
        
        return keyList;
    }

    @Override
    public List<IndexEntry> values() 
    {
        List<IndexEntry> valueList = new ArrayList();
        
        SimpleNodeList tmpNode = firstNode;
        
        while(tmpNode != null)
        {
            valueList.add(tmpNode.getValue());
            tmpNode = tmpNode.getNextNode();
        }
        
        return valueList;
    }

    @Override
    public void insert(String key, IndexEntry value) 
    {
        if(firstNode == null)
        {
            firstNode = new SimpleNodeList(key, value);
            lastNode = firstNode;
        }
        else
        {
            SimpleNodeList node = new SimpleNodeList(key, value);
            lastNode.setNextNode(node);
            lastNode = node;
        }
        
        listSize++;
    }

    @Override
    public IndexEntry get(String key)
    {
        SimpleNodeList tmpNode = firstNode;
        
        while(tmpNode != null)
        {
            if(tmpNode.getKey().equalsIgnoreCase(key))
            {
                return tmpNode.getValue();
            }
            
            tmpNode = tmpNode.getNextNode();
        }
        
        throw new NotFoundException("The key '" + key + "' is not in the list");
    }

    @Override
    public void remove(String key)
    {
        SimpleNodeList tmpPreviousNode = null;
        SimpleNodeList tmpNode = firstNode;
        
        while(tmpNode != null)
        {
            if(tmpNode.getKey().equalsIgnoreCase(key))
            {
                if(tmpNode.getKey().equalsIgnoreCase(firstNode.getKey()))
                {
                    firstNode = tmpNode.getNextNode();
                }
                else if(tmpNode.getKey().equalsIgnoreCase(lastNode.getKey()))
                {
                    tmpPreviousNode.setNextNode(null);
                    lastNode = tmpPreviousNode;
                }
                else
                {
                    tmpPreviousNode.setNextNode(tmpNode.getNextNode());
                }
                
                listSize--;
                
                if(listSize == 0)
                {
                    firstNode = null;
                    lastNode = firstNode;
                }
                
                break;
            }
            
            tmpPreviousNode = tmpNode;
            tmpNode = tmpNode.getNextNode();
        }
    }

    @Override
    public boolean empty() 
    {
        return (firstNode == null);
    }

    @Override
    public boolean contains(String key)
    {
        SimpleNodeList tmpNode = firstNode;
        
        while(tmpNode != null)
        {
            if(tmpNode.getKey().equalsIgnoreCase(key))
            {
                return true;
            }
            
            tmpNode = tmpNode.getNextNode();
        }
        
        return false;
    }

    @Override
    public int size() 
    {
        return listSize;
    }

    @Override
    public void print() 
    {
        SimpleNodeList tmpNode = firstNode;
        
        while(tmpNode != null)
        {
            System.out.print("(" + tmpNode.getKey() + ", " + tmpNode.getValue() + ") ");
            tmpNode = tmpNode.getNextNode();
        }
        
        System.out.println();
    }
}
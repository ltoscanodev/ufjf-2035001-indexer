package dev.ltoscano.indexer.structure.AVL;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.structure.AVL.Node.AVLNode;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.util.MemoryUtil;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ltosc
 */
public class AVL extends IndexStructure
{
    private AVLNode rootNode;
    private int itemCount;
    
    public AVL()
    {
        this.rootNode = null;
        this.itemCount = 0;
    }
    
    private void updateHeight(AVLNode node)
    {
        node.setHeight(1 + Math.max(height(node.getLeftNode()), height(node.getRightNode())));
    }
    
    private int height(AVLNode node)
    {
        return (node == null) ? -1 : node.getHeight();
    }
    
    private int getBalance(AVLNode node)
    {
        return (node == null) ? 0 : (height(node.getRightNode()) - height(node.getLeftNode()));
    }
    
    private AVLNode rebalance(AVLNode z)
    {
        updateHeight(z);
        
        int balance = getBalance(z);
        
        if(balance > 1)
        {
            if(height(z.getRightNode().getRightNode()) > height(z.getRightNode().getLeftNode()))
            {
                z = leftRotation(z);
            }
            else
            {
                z.setRightNode(rightRotation(z.getRightNode()));
                z = leftRotation(z);
            }
        }
        else if(balance < -1)
        {
            if(height(z.getLeftNode().getLeftNode()) > height(z.getLeftNode().getRightNode()))
            {
                z = rightRotation(z);
            }
            else
            {
                z.setLeftNode(leftRotation(z.getLeftNode()));
                z = rightRotation(z);
            }
        }
                
        return z;
    }
    
    private AVLNode rightRotation(AVLNode y)
    {
        AVLNode x = y.getLeftNode();
        AVLNode z = x.getRightNode();
        
        x.setRightNode(y);
        y.setLeftNode(z);
        
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    private AVLNode leftRotation(AVLNode y)
    {
        AVLNode x = y.getRightNode();
        AVLNode z = x.getLeftNode();
        
        x.setLeftNode(y);
        y.setRightNode(z);
        
        updateHeight(y);
        updateHeight(x);
        
        return x;
    }
    
    private AVLNode insertKey(AVLNode node, String key, IndexEntry value)
    {
        if(node == null)
            return new AVLNode(key, value);
        
        int keyComp = node.getKey().compareTo(key);
        
        if(keyComp == 0)
        {
            node.setValue(value);
        }
        else if(keyComp > 0)
        {
            node.setLeftNode(insertKey(node.getLeftNode(), key, value));
        }
        else
        {
            node.setRightNode(insertKey(node.getRightNode(), key, value));
        }
        
        return rebalance(node);
    }
    
    private void listKeys(AVLNode node, List<String> keyList)
    {
        if(node == null)
            return;
        
        keyList.add(node.getKey());
        
        listKeys(node.getLeftNode(), keyList);
        listKeys(node.getRightNode(), keyList);
    }
    
    private void listValues(AVLNode node, List<IndexEntry> valueList)
    {
        if(node == null)
            return;
        
        valueList.add(node.getValue());
        
        listValues(node.getLeftNode(), valueList);
        listValues(node.getRightNode(), valueList);
    }
    
    @Override
    public List<String> keys()
    {
        List<String> keyList = new ArrayList<>();
        listKeys(rootNode, keyList);
        
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
        
        rootNode = insertKey(rootNode, key, value);
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
        
        AVLNode tmpNode = rootNode;
        
        while(tmpNode != null)
        {
            if(tmpNode.getKey().equalsIgnoreCase(key))
            {
                break;
            }
            
            tmpNode = (tmpNode.getKey().compareTo(key) < 0) ? tmpNode.getRightNode() : tmpNode.getLeftNode();
        }
        
        return (tmpNode == null) ? null : tmpNode.getValue();
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
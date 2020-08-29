package dev.ltoscano.indexer.structure.List.Node;

import dev.ltoscano.indexer.model.IndexEntry;

/**
 *
 * @author ltosc
 */
public class DoubleNodeList
{
    private String key;
    private IndexEntry value;
    
    private DoubleNodeList previousNode;
    private DoubleNodeList nextNode;
    
    public DoubleNodeList(String key, IndexEntry value)
    {
        this(key, value, null, null);
    }
    
    public DoubleNodeList(String key, IndexEntry value, DoubleNodeList previousNode, DoubleNodeList nextNode)
    {
        this.key = key;
        this.value = value;
        
        this.previousNode = previousNode;
        this.nextNode = nextNode;
    }

    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public IndexEntry getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(IndexEntry value) {
        this.value = value;
    }

    /**
     * @return the previousNode
     */
    public DoubleNodeList getPreviousNode() {
        return previousNode;
    }

    /**
     * @param previousNode the previousNode to set
     */
    public void setPreviousNode(DoubleNodeList previousNode) {
        this.previousNode = previousNode;
    }

    /**
     * @return the nextNode
     */
    public DoubleNodeList getNextNode() {
        return nextNode;
    }

    /**
     * @param nextNode the nextNode to set
     */
    public void setNextNode(DoubleNodeList nextNode) {
        this.nextNode = nextNode;
    }
}

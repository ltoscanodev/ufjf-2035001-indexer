package dev.ltoscano.indexer.structure.List.Node;

import dev.ltoscano.indexer.model.IndexEntry;

/**
 *
 * @author ltosc
 */
public class SimpleNodeList
{
    private String key;
    private IndexEntry value;
    
    private SimpleNodeList nextNode;
    
    public SimpleNodeList(String key, IndexEntry value)
    {
        this(key, value, null);
    }
    
    public SimpleNodeList(String key, IndexEntry value, SimpleNodeList nextNode)
    {
        this.key = key;
        this.value = value;
        
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
     * @return the nextNode
     */
    public SimpleNodeList getNextNode() {
        return nextNode;
    }

    /**
     * @param nextNode the nextNode to set
     */
    public void setNextNode(SimpleNodeList nextNode) {
        this.nextNode = nextNode;
    }
}

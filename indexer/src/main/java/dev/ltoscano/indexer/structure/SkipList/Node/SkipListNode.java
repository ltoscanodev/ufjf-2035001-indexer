package dev.ltoscano.indexer.structure.SkipList.Node;

import dev.ltoscano.indexer.model.IndexEntry;

/**
 *
 * @author ltosc
 */
public class SkipListNode
{
    private String key;
    private IndexEntry value;
    
    private SkipListNode nextNode;
    private SkipListNode underNode;
    
    public SkipListNode(String key, IndexEntry value)
    {
        this(key, value, null, null);
    }
    
    public SkipListNode(String key, IndexEntry value, SkipListNode nextNode, SkipListNode underNode)
    {
        this.key = key;
        this.value = value;
        this.nextNode = nextNode;
        this.underNode = underNode;
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
    public SkipListNode getNextNode() {
        return nextNode;
    }

    /**
     * @param nextNode the nextNode to set
     */
    public void setNextNode(SkipListNode nextNode) {
        this.nextNode = nextNode;
    }

    /**
     * @return the underNode
     */
    public SkipListNode getUnderNode() {
        return underNode;
    }

    /**
     * @param underNode the underNode to set
     */
    public void setUnderNode(SkipListNode underNode) {
        this.underNode = underNode;
    }
}

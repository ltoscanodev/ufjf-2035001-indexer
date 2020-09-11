package dev.ltoscano.indexer.structure.AVL.Node;

import dev.ltoscano.indexer.model.IndexEntry;

/**
 *
 * @author ltosc
 */
public class AVLNode 
{
    private String key;
    private IndexEntry value;
    
    private int height;
    
    private AVLNode leftNode;
    private AVLNode rightNode;
    
    public AVLNode(String key, IndexEntry value)
    {
        this(key, value, 0, null, null);
    }
    
    public AVLNode(String key, IndexEntry value, int height, AVLNode leftNode, AVLNode rightNode)
    {
        this.key = key;
        this.value = value;
        this.height = height;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
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
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @return the leftNode
     */
    public AVLNode getLeftNode() {
        return leftNode;
    }

    /**
     * @param leftNode the leftNode to set
     */
    public void setLeftNode(AVLNode leftNode) {
        this.leftNode = leftNode;
    }

    /**
     * @return the rightNode
     */
    public AVLNode getRightNode() {
        return rightNode;
    }

    /**
     * @param rightNode the rightNode to set
     */
    public void setRightNode(AVLNode rightNode) {
        this.rightNode = rightNode;
    }
}

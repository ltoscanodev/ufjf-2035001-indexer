package dev.ltoscano.indexer.structure.Trie.Node;

import dev.ltoscano.indexer.model.IndexEntry;

/**
 *
 * @author ltosc
 */
public class TrieNode 
{
    private char key;
    private IndexEntry value;
    
    private TrieNode parentNode;
    private TrieNode[] childrenNodes;
    
    public TrieNode()
    {
        this('\0', null, null);
    }
    
    public TrieNode(char key, TrieNode parentNode, IndexEntry value)
    {
        this.key = key;
        this.value = value;
        
        this.parentNode = parentNode;
        this.childrenNodes = new TrieNode[36];
    }
    
    private int getChildIndex(char keyChar)
    {
        int offset;
            
        if(Character.isLowerCase(keyChar))
        {
            offset = 97;
        }
        else if(Character.isDigit(keyChar))
        {
            // 48 - 26
            offset = 22;
        }
        else
        {
            throw new IllegalArgumentException("Unknown character type");
        }
        
        return (keyChar - offset);
    }
    
    public boolean isKey()
    {
        return (value != null);
    }
    
    /**
     * @return the key
     */
    public char getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(char key) {
        this.key = key;
    }

    /**
     * @return the value
     */
    public IndexEntry getValue()
    {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(IndexEntry value)
    {
        this.value = value;
    }
    
    /**
     * @return the parentNode
     */
    public TrieNode getParentNode() {
        return parentNode;
    }

    /**
     * @param parentNode the parentNode to set
     */
    public void setParentNode(TrieNode parentNode) {
        this.parentNode = parentNode;
    }
    
    public TrieNode getChild(char keyChar)
    {
        int index = getChildIndex(keyChar);
        
        if((index < 0) || (index > childrenNodes.length))
            throw new IllegalArgumentException("Invalid index");
        
        return childrenNodes[index];
    }
    
    public void setChild(char keyChar, TrieNode node)
    {
        int index = getChildIndex(keyChar);
        
        if((index < 0) || (index > childrenNodes.length))
            throw new IllegalArgumentException("Invalid index");
        
        childrenNodes[index] = node;
    }

    /**
     * @return the childrenNodes
     */
    public TrieNode[] getChildrenNodes() 
    {
        return childrenNodes;
    }
}

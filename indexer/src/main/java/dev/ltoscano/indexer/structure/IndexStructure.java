package dev.ltoscano.indexer.structure;

import dev.ltoscano.indexer.model.IndexEntry;
import java.util.List;

/**
 *
 * @author ltosc
 */
public interface IndexStructure
{
    public enum IndexStructureType 
    {
        SimpleLinkedList(0), DoubleLinkedList(1), SkipList(2), HashTable(3), AVL(4), Trie(5);
        
        private final int value;

        private IndexStructureType(int value) 
        {
            this.value = value;
        }
        public int getValue() 
        {
            return value;
        }
    }
    
    public List<String> keys();
    public List<IndexEntry> values();
    
    public void insert(String key, IndexEntry value);
    public IndexEntry get(String key);
    public void remove(String key);
    
    public boolean empty();
    public boolean contains(String key);
    
    public int size();
    
    public void print();
}

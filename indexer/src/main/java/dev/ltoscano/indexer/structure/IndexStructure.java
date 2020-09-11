package dev.ltoscano.indexer.structure;

import dev.ltoscano.indexer.index.stats.IndexStructureStats;
import dev.ltoscano.indexer.model.IndexEntry;
import java.util.List;

/**
 *
 * @author ltosc
 */
public abstract class IndexStructure
{
    public enum IndexStructureType 
    {
        HashTable(0), AVL(1), SkipList(2), Trie(3);
        
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
    
    private final IndexStructureStats structureStats;
    
    public IndexStructure()
    {
        this.structureStats = new IndexStructureStats();
    }
    
    public abstract List<String> keys();
    public abstract List<IndexEntry> values();
    
    public abstract void insert(String key, IndexEntry value);
    public abstract IndexEntry get(String key);
    
    public abstract boolean empty();
    public abstract boolean contains(String key);
    
    public abstract int size();

    /**
     * @return the structureStats
     */
    public IndexStructureStats getStructureStats() {
        return structureStats;
    }
}

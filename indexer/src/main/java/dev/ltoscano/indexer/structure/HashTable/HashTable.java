package dev.ltoscano.indexer.structure.HashTable;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.structure.HashTable.Node.HashTableNode;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.util.MemoryUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Estrutura de dados do tipo tabela hash
 *
 * @author ltosc
 */
public class HashTable extends IndexStructure
{
    // Tamanho da tabela hash
    private int tableSize;
    // Tabela hash
    private HashTableNode[] table;
    // Quantidade de itens inseridos
    private int itemCount;
    
    public HashTable()
    {
        this(100);
    }
    
    public HashTable(int tableSize)
    {
        this.tableSize = tableSize;
        this.table = new HashTableNode[tableSize];
        
        this.itemCount = 0;
    }
    
    /**
     * Obtém o índice da tabela para a chave usando a função de hash (Polynomial rolling hash).
     * Faz uso do método de Horner para avaliação eficiente do polinômio.
     * 
     * @see https://cp-algorithms.com/string/string-hashing.html
     * @see https://en.wikipedia.org/wiki/Horner%27s_method
     * @param key
     * @param M
     * @return Índice da tabela
     */
    private int getIndex(String key, int M)
    {
        if(key.isEmpty())
        {
            // Retorna o índice da tabela
            return 0;
        }
        else
        {
            // Faz a avaliação do polinômio usando o método de Horner
            int val = key.charAt(0);
   
            for (int i = 1; i < key.length(); i++) 
            {
                val = val * 31 + key.charAt(i); 
            }
            
            // Retorna o índice da tabela
            return Math.abs(val % M);
        }
    }
    
    /**
     * Obtém o fator de carga da tabela
     * 
     * @return Fator de carga da tabela
     */
    private float getLoadFactor()
    {
        // Retorna o fator de carga da tabela de hash
        return ((float)itemCount / tableSize);
    }
    
    /**
     * Redimensiona a tabela para o dobro do tamanho atual
     */
    private void rehashing()
    {
        // Define um novo tamanho para a tabela (O dobro do atual)
	int newTableSize = (2 * tableSize);

	// Cria uma nova tabela com o novo tamanho
	HashTableNode[] newTable = new HashTableNode[newTableSize];

	// Percorre toda a tabela atual
	for (int i = 0; i < tableSize; i++)
	{
            HashTableNode tmpNode = table[i];
            
            // Para cada entrada da tabela não vazia
            while(tmpNode != null)
            {
                // Obtém o índice na nova tabela
                int newIndex = getIndex(tmpNode.getKey(), newTableSize);
                 
                // Insere a entrada na nova tabela
                if(newTable[newIndex] == null)
                {
                    newTable[newIndex] = new HashTableNode(tmpNode.getKey(), tmpNode.getValue());
                }
                else
                {
                    newTable[newIndex] = new HashTableNode(tmpNode.getKey(), tmpNode.getValue(), newTable[newIndex]);
                }
                
                // Avança para a próxima entrada (Encadeamento externo)
                tmpNode = tmpNode.getNextNode();
            }
	}

	// Atualiza o tamanho da tabela
	tableSize = newTableSize;

	// Atualiza a tabela de hash
	table = newTable;
    }
    
    @Override
    public List<String> keys() 
    {
        List<String> keyList = new ArrayList<>();
        
        for(int i = 0; i < tableSize; i++)
        {
            HashTableNode tmpNode = table[i];
            
            while(tmpNode != null)
            {
                keyList.add(tmpNode.getKey());
                tmpNode = tmpNode.getNextNode();
            }
        }
        
        return keyList;
    }

    @Override
    public List<IndexEntry> values() 
    {
        List<IndexEntry> valueList = new ArrayList<>();
        
        for(int i = 0; i < tableSize; i++)
        {
            HashTableNode tmpNode = table[i];
                
            while(tmpNode != null)
            {
                valueList.add(tmpNode.getValue());
                tmpNode = tmpNode.getNextNode();
            }
        }
        
        return valueList;
    }

    @Override
    public void insert(String key, IndexEntry value)
    {
        long startTime = System.nanoTime();
        
        // Verifica o fator de carga da tabela e faz o rehashing se necessário
	if (getLoadFactor() > 0.75)
            rehashing();
        
        int index = getIndex(key, tableSize);
        
        if(table[index] == null)
        {
            table[index] = new HashTableNode(key, value);
        }
        else
        {   
            table[index] = new HashTableNode(key, value, table[index]);
        }
        
        itemCount++;
        
        if((itemCount % AppConfig.insertLogTime) == 0)
        {
            getStructureStats().getInsertTimeList().add(System.nanoTime() - startTime);
        }
        
        if((itemCount % AppConfig.memoryLogTime) == 0)
        {
            getStructureStats().getMemoryUsageList().add(MemoryUtil.getMemoryUsage());
        }
    }

    @Override
    public IndexEntry get(String key) 
    {
        int index = getIndex(key, tableSize);
        
        HashTableNode tmpNode = table[index];
        
        while((tmpNode != null) && !tmpNode.getKey().equalsIgnoreCase(key))
        {
            tmpNode = tmpNode.getNextNode();
        }
        
        return ((tmpNode != null) && tmpNode.getKey().equalsIgnoreCase(key)) ? tmpNode.getValue() : null;
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

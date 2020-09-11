package dev.ltoscano.indexer.structure.SkipList;

import dev.ltoscano.indexer.model.IndexEntry;
import dev.ltoscano.indexer.structure.IndexStructure;
import dev.ltoscano.indexer.structure.SkipList.Node.SkipListNode;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author ltosc
 */
public class SkipList extends IndexStructure
{
    private SkipListNode head;
    
    private int maxLevel;
    private double p;
    
    private int itemCount;
    
    public SkipList()
    {
        this(0.5);
    }
    
    public SkipList(double p)
    {
        this.head = null;
        
        this.maxLevel = 0;
        this.p = p;
        
        this.itemCount = 0;
    }
    
    private double getRandomNumber()
    {
        // Gera um número pseudo-aleatório no intervalo [0.0, 1.0]
        return Math.random();
    }
    
    private int getLevelCount()
    {
        int levelCount = 0;

        // Obtém um número aleatório
        double randomNumber = getRandomNumber();

        // Repete enquanto o número aleatório gerado for menor que o fator 'p'
        while (randomNumber < p)
        {
            // Aumenta a quantidade de níveis
            levelCount++;
            // Obtém um novo número aleatório
            randomNumber = getRandomNumber();
        }

        // Retorna a quantidade de níveis
        return levelCount;
    }

    @Override
    public List<String> keys() 
    {
        List<String> keyList = new ArrayList<>();
        
        SkipListNode tmpNode = head;

	while (tmpNode.getUnderNode() != null)
	{
            tmpNode = tmpNode.getUnderNode();
	}

	while (tmpNode != null)
	{
            keyList.add(tmpNode.getKey());
            tmpNode = tmpNode.getNextNode();
	}
        
        return keyList;
    }

    @Override
    public List<IndexEntry> values()
    {
        List<IndexEntry> valueList = new ArrayList<>();
        
        SkipListNode tmpNode = head;

	while (tmpNode.getUnderNode() != null)
	{
            tmpNode = tmpNode.getUnderNode();
	}

	while (tmpNode != null)
	{
            valueList.add(tmpNode.getValue());
            tmpNode = tmpNode.getNextNode();
	}
        
        return valueList;
    }

    @Override
    public void insert(String key, IndexEntry value) 
    {
        // Inserindo em uma Skiplist vazia
	if (empty())
	{
            // Cria o primeiro nó da Skiplist e faz 'head' apontar para este novo nó
            head = new SkipListNode(key, value);
	}
	else
	{
            // Se está inserindo um valor menor que a cabeça da Skiplist
            if (head.getKey().compareTo(key) < 0)
            {
                // Cria uma nova cabeça para a Skiplist
                SkipListNode tmpNewHeadNode = new SkipListNode(key, value, head, null);
                SkipListNode tmpHeadNode = head.getUnderNode();
                head = tmpNewHeadNode;

                SkipListNode tmpNode;

                // Cria novas pontas para cada uma das listas de níveis da Skiplist
                for (int i = (maxLevel - 1); i >= 0; i--)
                {
                    tmpNode = new SkipListNode(key, value, tmpHeadNode, null);
                    tmpNewHeadNode.setUnderNode(tmpNode);

                    tmpNewHeadNode = tmpNewHeadNode.getUnderNode();
                    tmpHeadNode = tmpHeadNode.getUnderNode();
                }
            }
            else
            {
                // Sorteia a quantidade de níveis que o nó terá
                int levelCount = getLevelCount();

                // Se o número de níveis ultrapassa a quantidade de níveis atuais da Skiplist
                if (maxLevel < levelCount)
                {
                    // Ajusta os níveis da Skiplist aumentando o nível de 'head'
                    int sizeDiff = Math.abs(maxLevel - levelCount);
                    SkipListNode tmpNewNode;

                    for (int i = 0; i < sizeDiff; i++)
                    {
                        tmpNewNode = new SkipListNode(head.getKey(), head.getValue(), null, head);
                        head = tmpNewNode;
                    }

                    // Atualiza o nível atual da Skiplist
                    maxLevel = levelCount;
                }

                // Empilha o ponteiros de início de cada nível da lista
                Stack<SkipListNode> nodePointerStack = new Stack<>();
                SkipListNode tmpNode = head;

                while (tmpNode != null)
                {
                    nodePointerStack.push(tmpNode);
                    tmpNode = tmpNode.getUnderNode();
                }

                SkipListNode underNode = null;

                // Repete do nível mais inferior até o nível 'levelCount' sorteado
                for (int i = 0; i <= levelCount; i++)
                {
                    // Obtém o ponteiro inicial da lista a partir do topo da pilha
                    tmpNode = nodePointerStack.peek();

                    // Procura na lista a posição do nó a ser inserido
                    while ((tmpNode.getNextNode() != null) && (tmpNode.getNextNode().getKey().compareTo(key) < 0))
                    {
                          tmpNode = tmpNode.getNextNode();
                    }

                    // Insere o novo nó e ajusta os ponteiros
                    SkipListNode newNode = new SkipListNode(key, value, tmpNode.getNextNode(), underNode);
                    tmpNode.setNextNode(newNode);
                    underNode = newNode;

                    // Desempilha o ponteiro inicial da lista
                    nodePointerStack.pop();
                }
            }
	}
        
        itemCount++;
    }

    @Override
    public IndexEntry get(String key) 
    {
        SkipListNode tmpNode = head;

	while (tmpNode != null)
	{
            // Se o valor do nó é igual ao valor buscado, o valor está contido na Skiplist
            if (tmpNode.getKey().equalsIgnoreCase(key))
                // O valor foi encontrado
                return tmpNode.getValue();
            // Caso contrário, o próximo nó é diferente de null e tem valor menor que o valor buscado?
            else if ((tmpNode.getNextNode() != null) && (tmpNode.getNextNode().getKey().compareTo(key) >= 0))
                // Se sim, avança para o próximo nó
                tmpNode = tmpNode.getNextNode();
            else
                // Senão, desce no nível da Skiplist
                tmpNode = tmpNode.getUnderNode();
	}

	// A Skiplist foi percorrida e o valor não foi encontrado
	return null;
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

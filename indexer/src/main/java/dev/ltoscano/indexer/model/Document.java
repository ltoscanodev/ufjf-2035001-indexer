package dev.ltoscano.indexer.model;

/**
 *  Classe que armazena o id de uma notícia e o peso de um termo neste documento
 * 
 * @author ltosc
 */
public class Document 
{
    private final int id;
    private double weight;
    
    public Document(int id)
    {
        this.id = id;
        this.weight = 0.0;
    }
    
    public Document(int id, double weight)
    {
        this.id = id;
        this.weight = weight;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the weight
     */
    public double getWeight() {
        return weight;
    }

    /**
     * @param weight the weight to set
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}

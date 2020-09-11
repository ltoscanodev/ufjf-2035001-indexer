package dev.ltoscano.indexer.util;

/**
 *
 * @author ltosc
 */
public class MemoryUtil
{
    /**
     * Obtém o uso de memória aproximado da aplicação
     * 
     * @return Uso aproximado de memória pela aplicação (Em bytes)
     */
    public static long getMemoryUsage()
    {
        Runtime runtime = Runtime.getRuntime();
        // Faz a coleta de lixo
        runtime.gc();
        // Retorna o uso de memória aproximada
        return (runtime.totalMemory() - runtime.freeMemory());
    }
}

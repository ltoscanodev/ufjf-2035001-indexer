package dev.ltoscano.indexer.util;

/**
 *
 * @author ltosc
 */
public class MemoryUtil
{
    public static long getMemoryUsage()
    {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        return (runtime.totalMemory() - runtime.freeMemory());
    }
}

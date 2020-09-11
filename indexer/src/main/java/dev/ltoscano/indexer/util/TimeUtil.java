package dev.ltoscano.indexer.util;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author ltosc
 */
public class TimeUtil 
{
    public static long convertNanoToSeconds(long time)
    {
        return TimeUnit.SECONDS.convert(time, TimeUnit.NANOSECONDS);
    }
    
    public static long convertNanoToMilliseconds(long time)
    {
        return TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
    }
}

package dev.ltoscano.indexer.exception;

/**
 *
 * @author ltosc
 */
public class NotFoundException extends RuntimeException
{
    public NotFoundException()
    {
        super();
    }
    
    public NotFoundException(String msg)
    {
        super(msg);
    }
    
    public NotFoundException(String msg, Throwable ex)
    {
        super(msg, ex);
    }
}
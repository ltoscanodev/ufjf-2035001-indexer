package dev.ltoscano.indexer;

import dev.ltoscano.indexer.configuration.AppConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IndexerApplication 
{
    private static void setRunMode(int type)
    {
        switch(type)
        {
            case 0:
            {
                AppConfig.runMode = AppConfig.RunMode.Web;
                break;
            }
            case 1:
            {
                AppConfig.runMode = AppConfig.RunMode.Console;
                break;
            }
            case 2:
            {
                AppConfig.runMode = AppConfig.RunMode.Test;
                break;
            }
            default:
            {
                AppConfig.runMode = AppConfig.RunMode.Web;
            }
        }
    }
    
    private static void parseArgs(String[] args)
    {
        if(args.length >= 1)
        {
            setRunMode(Integer.valueOf(args[0]));
        }
    }
    
    public static void main(String[] args) 
    {
        parseArgs(args);
        
        switch(AppConfig.runMode)
        {
            case Web:
            {
                break;
            }
            case Console:
            {
                IndexerConsoleApplication.main(args);
                break;
            }
            case Test:
            {
                break;
            }
            default:
            {
                
            }
        }
    }
}

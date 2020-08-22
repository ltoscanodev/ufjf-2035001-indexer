package dev.ltoscano.indexer.controller;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ltosc
 */
@Controller
public class IndexerController 
{
    @RequestMapping("/")
    public String index() 
    {
        return "loading";
    }
    
    @RequestMapping(value = "/loading")
    @ResponseBody
    public Map loading() 
    {
        try 
        {
            Thread.sleep(3000);
        } 
        catch (InterruptedException ex) 
        {
            Logger.getLogger(IndexerController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return Collections.singletonMap("redirect_url", "/indexer");
    }
    
    @RequestMapping("/indexer")
    public String indexer() 
    {
        return "indexer";
    }
}

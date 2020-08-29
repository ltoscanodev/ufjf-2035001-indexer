package dev.ltoscano.indexer.controller;

import dev.ltoscano.indexer.configuration.AppConfig;
import dev.ltoscano.indexer.index.Index;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author ltosc
 */
@Controller
public class IndexerController 
{
    private Index index;

    @RequestMapping("/")
    public String index()
    {
        return "/loading";
    }
    
    @RequestMapping(value = "/loaded")
    @ResponseBody
    public Map loaded()
    {
        return Collections.singletonMap("loaded", (index != null));
    }

    @RequestMapping(value = "/loading")
    @ResponseBody
    public Map loading()
    {
        if (index == null) 
        {
            try
            {
                index = new Index(AppConfig.datasetPath);
            } 
            catch (IOException ex)
            {
                Logger.getLogger(IndexerController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return Collections.singletonMap("redirect_url", "/search");
    }

    @RequestMapping("/search")
    public String search() 
    {
        return "/search";
    }

    @RequestMapping("/result")
    public String result(Model model, @RequestParam(name = "q", required = false) List<String> query)
    {
        if((index == null) || (query == null))
        {
            return "/search";
        }
        else
        {
            model.addAttribute("resultList", index.query(query));
            return "/result";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Model model, HttpServletRequest req, Exception ex)
    {
        model.addAttribute("exception", ex);
        model.addAttribute("url", req.getRequestURL());
        
        return "/error";
    }
}
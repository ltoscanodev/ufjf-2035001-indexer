package dev.ltoscano.indexer.controller;

import dev.ltoscano.indexer.index.Index;
import dev.ltoscano.indexer.index.stats.IndexStats;
import dev.ltoscano.indexer.index.stats.QueryStats;
import dev.ltoscano.indexer.model.QueryResult;
import dev.ltoscano.indexer.util.TimeUtil;
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
 * Controlador das Views do sistema (Padrão MVC)
 * 
 * @author ltosc
 */
@Controller
public class IndexerController 
{
    // Índice de notícias
    private Index index;

    @RequestMapping("/")
    public String index()
    {
        // Retorna a página inicial do sistema
        return "/loading";
    }
    
    @RequestMapping(value = "/loaded")
    @ResponseBody
    public Map loaded()
    {
        // Retorna um JSON informando se o índice de notícias está criado ou não
        return Collections.singletonMap("loaded", (index != null));
    }

    @RequestMapping(value = "/loading")
    @ResponseBody
    public Map loading()
    {
        // Verifica se o índice de notícias não foi criado ainda
        if (index == null) 
        {
            try
            {
                // Cria um novo índice utilizando o dataset de notícias indicado
                // pela configuração 'AppConfig.datasetPath'
                index = new Index();
            } 
            catch (IOException ex)
            {
                // Faz o log do erro
                Logger.getLogger(IndexerController.class.getName()).log(Level.SEVERE, null, ex);
                // Rediciona para a página de erro
                return Collections.singletonMap("redirect_url", "/error");
            }
        }

        // Redireciona para a página de busca
        return Collections.singletonMap("redirect_url", "/search");
    }

    @RequestMapping("/search")
    public String search() 
    {
        // Retorna a página de busca
        return "/search";
    }

    @RequestMapping("/result")
    public String result(Model model, @RequestParam(name = "q", required = false) List<String> query)
    {
        // Verifica se o índice ainda não foi criado ou se a consulta é vazia
        if((index == null) || (query == null))
        {
            // Retorna para a página inicial
            return "/loading";
        }
        else
        {
            // Faz a consulta no índice de notícias a partir da lista de termos
            List<QueryResult> resultList = index.query(query);
            
            // Preenche o modelo com o tempo da consulta
            model.addAttribute("queryTime", TimeUtil.convertNanoToMilliseconds(index.getQueryStats().getLastTotalQueryTime()));
            // Preenche o modelo com o resultado da consulta ao índice
            model.addAttribute("resultList", resultList);
            
            // Retorna a página de resultados
            return "/result";
        }
    }
    
    @RequestMapping("/stats")
    public String stats(Model model)
    {
        // Verifica se o índice ainda não foi criado
        if(index == null)
        {
            // Retorna para a página inicial
            return "/loading";
        }
        else
        {
            // Obtém as estatícias do índice
            IndexStats indexStats = index.getIndexStats();
            
            // Preenche o modelo com as estatísticas do índice
            model.addAttribute("datasetSize", indexStats.getDatasetSize());
            model.addAttribute("indexEntries", indexStats.getIndexEntries());
            model.addAttribute("indexBuildTime", TimeUtil.convertNanoToSeconds(indexStats.getBuildTime()));
            
            // Obtém as estatísticas da última consulta
            QueryStats queryStats = index.getQueryStats();

            // Reconstrói a string de busca
            String query = "";

            for(String word : queryStats.getLastQuery())
            {
                query += word + " ";
            }

            // Preenche o modelo com as estatísticas da última consulta realizada
            model.addAttribute("queryStats", true);
            model.addAttribute("query", query);
            model.addAttribute("queryTime", TimeUtil.convertNanoToMilliseconds(queryStats.getLastTotalQueryTime()));
            model.addAttribute("queryResultList", queryStats.getLastQueryResult());
            
            // Retorna a página de estatísticas
            return "/stats";
        }
    }

    @ExceptionHandler(Exception.class)
    public String handleError(Model model, HttpServletRequest req, Exception ex)
    {
        // Faz o log do erro
        Logger.getLogger(IndexerController.class.getName()).log(Level.SEVERE, null, ex);
        
        // Retorna a página de erro
        return "/error";
    }
}
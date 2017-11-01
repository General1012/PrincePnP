
package com.pnp.pnp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

// controles mapping of a page
@Controller
public class SiteController {
    
    @RequestMapping(value="/")
    public String getIndex()
    {
        return "index";
    }
    @RequestMapping(value="/{page}")
    public String getPage(@PathVariable("page") String page)
    {
        return page;
    }  

    
}


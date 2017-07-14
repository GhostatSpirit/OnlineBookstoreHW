package com.lykavin.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lykav on 7/14/2017.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap){
        return "admin/index";
    }


}

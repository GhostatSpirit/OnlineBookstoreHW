package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by lykav on 7/14/2017.
 */

@Controller
@RequestMapping("/admin")
public class AdminController {

    @RequestMapping(value = "")
    public String index(){
        return "/admin/index";
    }

    @RequestMapping(value = "/login")
    public String login(){
        return "/admin/login";
    }



}

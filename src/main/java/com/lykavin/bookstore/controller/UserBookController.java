package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.service.BookService;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.Column;
import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lykav on 7/16/2017.
 */
@Controller
public class UserBookController {

    @Autowired
    UserService userService;
    @Autowired
    BookService bookService;

    @RequestMapping("/bookshelf")
    public String bookshelf(Model model){
        List<BookEntity> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "bookshelf";
    }

    @RequestMapping("/bookDetail")
    public String bookDetail(@PathParam("id") Long id, Model model, Principal principal){
        if(principal != null){
            String username = principal.getName();
            UserEntity user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        BookEntity book = bookService.findOne(id);
        model.addAttribute("book", book);

//        // the quantity that user can choose
//        List<Integer> qtyList = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//        model.addAttribute("qtyList", qtyList);
//        model.addAttribute("qty", 1);   // default quantity would be 1

        return "bookDetail";
    }
}

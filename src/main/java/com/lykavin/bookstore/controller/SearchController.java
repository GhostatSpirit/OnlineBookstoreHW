package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.model.UserEntity;
import com.lykavin.bookstore.service.BookService;
import com.lykavin.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

/**
 * Created by lykav on 7/17/2017.
 */
@Controller
public class SearchController {
    @Autowired
    private UserService userService;

    private BookService bookService;

    @Autowired
    public SearchController(BookService bookService) {
        this.bookService = bookService;
    }

    @RequestMapping("/searchByCategory")
    public String searchByCategory(
            @RequestParam("category") String category,
            Model model, Principal principal
    ){
        if(principal!=null) {
            String username = principal.getName();
            UserEntity user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        String classActiveCategory = "active"+category;
        classActiveCategory = classActiveCategory.replaceAll("\\s+", "");
        classActiveCategory = classActiveCategory.replaceAll("&", "");
        model.addAttribute(classActiveCategory, true);

        List<BookEntity> bookList = bookService.findByCategory(category);

        if (bookList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "bookshelf";
        }

        model.addAttribute("bookList", bookList);

        return "bookshelf";
    }

    @RequestMapping("/searchBook")
    public String searchBook(
            @ModelAttribute("keyword") String keyword,
            Principal principal, Model model
    ) {
        if(principal!=null) {
            String username = principal.getName();
            UserEntity user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        List<BookEntity> bookList = bookService.blurrySearch(keyword);

        if (bookList.isEmpty()) {
            model.addAttribute("emptyList", true);
            return "bookshelf";
        }

        model.addAttribute("bookList", bookList);

        return "bookshelf";
    }
}

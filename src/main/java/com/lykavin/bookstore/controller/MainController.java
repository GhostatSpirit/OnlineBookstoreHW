package com.lykavin.bookstore.controller;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.repository.BookRepository;
import com.lykavin.bookstore.repository.UserRepository;
import com.lykavin.bookstore.utility.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lykav on 2017/4/23.
 */
@Controller
public class MainController {

    @Autowired
    BookRepository bookRepo;


    private static final int columnCount = 3;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap modelMap) {
        // get all books from book repository
        // pack them as bookList 3 by 3
        // to suit frontend
        List<BookEntity> bookList = bookRepo.findAll();
        // initialize columned list
        List<List<BookEntity>> bookListColumned = new ArrayList<List<BookEntity>>();

        int bookCount = bookList.size();
        for(int i = 0; i < bookCount; i += columnCount){
            List<BookEntity> bookColumn = new ArrayList<BookEntity>();
            for(int j = i; j < Math.min(i + columnCount, bookCount); ++j){
                bookColumn.add(bookList.get(j));
            }
            bookListColumned.add(bookColumn);
        }

        modelMap.addAttribute("bookListColumned", bookListColumned);

        return "index";
    }

}

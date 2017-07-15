package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.repository.BookRepository;
import com.lykavin.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lykav on 7/15/2017.
 */
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    BookRepository bookRepo;

    @Override
    public BookEntity save(BookEntity book) {
        return bookRepo.save(book);
    }
}

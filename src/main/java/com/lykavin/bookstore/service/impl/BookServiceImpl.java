package com.lykavin.bookstore.service.impl;

import com.lykavin.bookstore.model.BookEntity;
import com.lykavin.bookstore.repository.BookRepository;
import com.lykavin.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public BookEntity findOne(Long id) {
        return bookRepo.findOne(id);
    }

    @Override
    public List<BookEntity> findAll() {
        return bookRepo.findAll();
    }

    @Override
    public List<BookEntity> findByCategory(String category) {
        List<BookEntity> bookList = bookRepo.findByCategory(category);

        List<BookEntity> activeBookList = new ArrayList<>();

        for (BookEntity book: bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }

        return activeBookList;
    }

    @Override
    public List<BookEntity> blurrySearch(String title) {
        List<BookEntity> bookList = bookRepo.findByTitleContaining(title);
        List<BookEntity> activeBookList = new ArrayList<>();

        for (BookEntity book: bookList) {
            if(book.isActive()) {
                activeBookList.add(book);
            }
        }
        return activeBookList;
    }
}
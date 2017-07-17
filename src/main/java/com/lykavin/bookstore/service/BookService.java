package com.lykavin.bookstore.service;

import com.lykavin.bookstore.model.BookEntity;

import java.util.List;

/**
 * Created by lykav on 7/15/2017.
 */
public interface BookService {
    BookEntity save(BookEntity book);

    BookEntity findOne(Long id);
    List<BookEntity> findAll();


    List<BookEntity> findByCategory(String category);

    List<BookEntity> blurrySearch(String title);
}

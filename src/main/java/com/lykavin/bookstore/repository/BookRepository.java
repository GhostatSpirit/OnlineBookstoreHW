package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Created by lykav on 2017/4/23.
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {
//
//    @Modifying
//    @Transactional
//    @Query("update BookEntity book " +
//            "set book.title=:qTitle, book.price=:qPrice, " +
//            "book.description=:qDescription, book.author=:qAuthor, " +
//            "book.img=:qImg " +
//            "where book.bid=:qBid")
//    void updateBook(@Param("qTitle") String title, @Param("qPrice") int price,
//                    @Param("qDescription") String description, @Param("qAuthor") String author,
//                    @Param("qImg") String img, @Param("qBid") Integer bid);
}

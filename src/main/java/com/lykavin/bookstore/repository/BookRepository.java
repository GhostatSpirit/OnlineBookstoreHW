package com.lykavin.bookstore.repository;

import com.lykavin.bookstore.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by lykav on 2017/4/23.
 */
@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByCategory(String category);

    List<BookEntity> findByTitleContaining(String title);
}

package com.sunwoo.bookdam.domain.book.repository;

import com.sunwoo.bookdam.domain.book.entity.BookEntity;
import com.sunwoo.bookdam.domain.book.entity.BookRatingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    Page<BookEntity> findAllByIsDeletedFalse(Pageable pageable);
    Optional<BookEntity> findByIdAndIsDeletedFalse(Long id);
    Optional<BookEntity> findByIsbnAndIsDeletedFalse(String isbn);
}


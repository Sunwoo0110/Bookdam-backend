package com.sunwoo.bookdam.domain.book.repository;

import com.sunwoo.bookdam.domain.book.dto.BookRatingStats;
import com.sunwoo.bookdam.domain.book.entity.BookEntity;
import com.sunwoo.bookdam.domain.book.entity.BookRatingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookRatingRepository extends JpaRepository<BookRatingEntity, Long> {
    // 해당 책의 리뷰 목록 (페이징)
    Page<BookRatingEntity> findByBookAndIsDeletedFalse(BookEntity book, Pageable pageable);

    // 해당 책의 리뷰 평점 평균
    // 리뷰 변경 시마다 평균 다시 계산하는 용도의 쿼리문
    @Query("""
            SELECT\s
              new com.sunwoo.bookdam.domain.book.dto.BookRatingStats(COUNT(r), AVG(r.rating))\s
            FROM BookRatingEntity r\s
            WHERE r.book = :book AND r.isDeleted = false
       \s""")
    BookRatingStats findStatsByBook(@Param("book") BookEntity book);
}


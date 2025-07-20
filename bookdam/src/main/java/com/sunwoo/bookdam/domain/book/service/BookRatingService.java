package com.sunwoo.bookdam.domain.book.service;

import com.sunwoo.bookdam.domain.book.dto.BookRatingCreateReqDto;
import com.sunwoo.bookdam.domain.book.dto.BookRatingPageableReqDto;
import com.sunwoo.bookdam.domain.book.dto.BookRatingResDto;
import org.springframework.data.domain.Page;

public interface BookRatingService {
    /** BookId으로 평점 및 리뷰 추가하고 평점 및 리뷰 상세 정보를 반환 */
    BookRatingResDto addRating(Long bookId, BookRatingCreateReqDto req);

    /** BookId 책의 평점 및 리뷰 목록을 페이지 단위로 조회 */
    Page<BookRatingResDto> listRatings(Long bookId, BookRatingPageableReqDto req);
}


package com.sunwoo.bookdam.domain.book.service;

import com.sunwoo.bookdam.domain.book.dto.*;
import org.springframework.data.domain.Page;

public interface BookService {
    /** 검색 조건에 맞는 책 목록을 페이지 단위로 조회 */
    Page<BookListResDto> listBooks(BookSearchReqDto req);

    /** Book ID로 조회하여 상세 정보를 반환 */
    BookDetailResDto getBook(Long bookId);

    /** 새로운 책 등록하고 등록된 책의 상세 정보를 반환 */
    BookDetailResDto createBook(BookCreateReqDto req);

    /** BookID의 책을 수정하고 수정된 책의 상세 정보를 반환 */
    BookDetailResDto updateBook(Long bookId, BookUpdateReqDto req);

    /** BookID의 책을 소프트 삭제(soft delete) 처리 */
    void softDeleteBook(Long bookId);

    /** BookID의 책을 하드 삭제(hard delete) 처리 */
    void hardDeleteBook(Long bookId);
}

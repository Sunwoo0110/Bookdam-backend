package com.sunwoo.bookdam.domain.book;

import com.sunwoo.bookdam.common.model.response.CommonBaseResult;
import com.sunwoo.bookdam.domain.book.dto.*;
import com.sunwoo.bookdam.domain.book.service.BookService;
import com.sunwoo.bookdam.domain.book.service.BookRatingService;
import com.sunwoo.bookdam.common.model.response.BaseResponse;
import com.sunwoo.bookdam.common.model.response.CommonResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.time.Duration;

@Slf4j
@Tag(name = "03. Book", description = "책 및 평점, 리뷰 관련 API")
@RestController
@RequestMapping("/v1/books")
@RequiredArgsConstructor
@Validated
public class BookController {
    private final BaseResponse baseResponse;
    private final BookService  bookService;
    private final BookRatingService bookRatingService;

    /** 책 목록 조회 및 검색 (200 OK + Cache 60s) */
    @GetMapping
    @Operation(summary = "책 목록 조회 및 검색", description = "제목,저자,정렬,페이징 파라미터로 책 목록을 조회합니다.")
    public ResponseEntity<CommonResult<Page<BookListResDto>>> listBooks(
            @ParameterObject BookSearchReqDto req
    ) {
        Page<BookListResDto> page = bookService.listBooks(req);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(baseResponse.getContentResult(page));
    }

    /** 책 상세 조회 (200 OK + Cache 60s) */
    @GetMapping("/{bookId}")
    @Operation(summary = "책 상세 조회", description = "bookId로 책 상세 정보를 조회합니다.")
    public ResponseEntity<CommonResult<BookDetailResDto>> getBook(
            @PathVariable Long bookId
    ) {
        BookDetailResDto dto = bookService.getBook(bookId);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(baseResponse.getContentResult(dto));
    }

    /** 책 등록 (201 Created + Location 헤더) */
    @PostMapping
    @Operation(summary = "책 등록", description = "새로운 책을 등록합니다.")
    public ResponseEntity<CommonResult<BookDetailResDto>> createBook(
            @RequestBody @Valid BookCreateReqDto req
    ) {
        BookDetailResDto created = bookService.createBook(req);
        URI location = URI.create("/v1/books/" + created.getId());
        return ResponseEntity
                .created(location)
                .body(baseResponse.getContentResult(created));
    }

    /** 책 수정 (200 OK) */
    @PatchMapping("/{bookId}")
    @Operation(summary = "책 수정", description = "bookId로 지정한 책 정보를 업데이트합니다.")
    public ResponseEntity<CommonResult<BookDetailResDto>> updateBook(
            @PathVariable Long bookId,
            @RequestBody @Valid BookUpdateReqDto req
    ) {
        BookDetailResDto updated = bookService.updateBook(bookId, req);
        return ResponseEntity.ok(baseResponse.getContentResult(updated));
    }

    /** 책 소프트 삭제 (204 No Content) */
    @PatchMapping("/{bookId}/soft-delete")
    @Operation(summary = "책 소프트 삭제", description = "bookId로 지정한 책을 소프트 삭제합니다.")
    public ResponseEntity<CommonBaseResult> softDeleteBook(
            @PathVariable Long bookId
    ) {
        bookService.softDeleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    /** 책 하드 삭제 (204 No Content) */
    @DeleteMapping("/{bookId}")
    @Operation(summary = "책 하드 삭제", description = "bookId로 지정한 책을 하드 삭제합니다.")
    public ResponseEntity<CommonBaseResult> hardDeleteBook(
            @PathVariable Long bookId
    ) {
        bookService.hardDeleteBook(bookId);
        return ResponseEntity.noContent().build();
    }

    /** 평점·리뷰 등록 (201 Created) */
    @PostMapping("/{bookId}/ratings")
    @Operation(summary = "평점·리뷰 등록", description = "bookId로 지정한 책에 평점과 리뷰를 등록합니다.")
    public ResponseEntity<CommonResult<BookRatingResDto>> addRating(
            @PathVariable Long bookId,
            @RequestBody @Valid BookRatingCreateReqDto req
    ) {
        BookRatingResDto rating = bookRatingService.addRating(bookId, req);
        URI location = URI.create("/v1/books/" + bookId + "/ratings/" + rating.getId());
        return ResponseEntity
                .created(location)
                .body(baseResponse.getContentResult(rating));
    }

    /** 평점·리뷰 목록 조회 (200 OK + Cache 60s) */
    @GetMapping("/{bookId}/ratings")
    @Operation(summary = "평점·리뷰 목록 조회", description = "bookId로 지정한 책의 평점, 리뷰 목록을 조회합니다.")
    public ResponseEntity<CommonResult<Page<BookRatingResDto>>> listRatings(
            @PathVariable Long bookId,
            @ParameterObject BookRatingPageableReqDto req
    ) {
        Page<BookRatingResDto> page = bookRatingService.listRatings(bookId, req);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(Duration.ofSeconds(60)))
                .body(baseResponse.getContentResult(page));
    }
}


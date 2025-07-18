package com.sunwoo.bookdam.domain.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * 책 검색 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class BookSearchReqDto {
    @Schema(description = "책 제목(검색, optional)", example = "해리포터")
    private String title;

    @Schema(description = "저자명(검색, optional)", example = "J.K.롤링")
    private String author;

    @Schema(description = "정렬 기준 (예: rating,desc 또는 latest)", example = "rating,desc")
    private String sort;

    @Schema(description = "페이지 번호 (1부터 시작, default=1)", example = "1")
    @Min(1)
    private Integer page;

    @Schema(description = "페이지 크기 (default=20, 최대=100)", example = "20")
    @Min(1) @Max(100)
    private Integer size;
}
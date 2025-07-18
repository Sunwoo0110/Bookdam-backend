package com.sunwoo.bookdam.domain.book.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * 책 수정 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class BookUpdateReqDto {
    @Schema(description = "도서명 (optional)", example = "토비의 스프링 3.1")
    private String title;

    @Schema(description = "저자명 (optional)", example = "이일민")
    private String author;

    @Schema(description = "표지 이미지 URL (optional)", example = "http://example.com/cover_updated.jpg")
    private String coverImage;

    @Schema(description = "설명 (optional)", example = "최신 내용 보강")
    private String description;

    @Schema(description = "평점 등록 건수 (optional)", example = "123")
    private Integer ratingCount;

    @Schema(description = "평균 평점 (optional)", example = "4.7")
    private Double ratingAvg;
}
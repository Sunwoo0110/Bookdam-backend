package com.sunwoo.bookdam.domain.book.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.*;

/**
 * 책 검색 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookSearchReqDto {

    @Schema(
            description  = "책 제목(검색, optional)",
            example      = "해리포터",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private String title;

    @Schema(
            description  = "저자명(검색, optional)",
            example      = "J.K.롤링",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private String author;

    @Schema(
            description  = "정렬 기준 (예: ratingAvg,desc 또는 createdAt,asc)",
            example      = "ratingAvg,desc",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Pattern(
            regexp  = "^[a-zA-Z0-9]+(Avg)?,(asc|desc)$",
            message = "sort는 '필드명,asc' 또는 '필드명,desc' 형식이어야 합니다."
    )
    private String sort;

    @Schema(
            description  = "페이지 번호 (1부터 시작, 기본=1)",
            example      = "1",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Min(1)
    private Integer page;

    @Schema(
            description  = "페이지 크기 (기본=20, 최대=100)",
            example      = "20",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Min(1) @Max(100)
    private Integer size;

    // 기본값 보장
    public int getPage() {
        return page == null ? 1 : page;
    }

    public int getSize() {
        return size == null ? 20 : size;
    }

    // 입력 정규화
    public void setTitle(String title) {
        this.title = (title == null ? null : title.trim());
    }

    public void setAuthor(String author) {
        this.author = (author == null ? null : author.trim());
    }
}
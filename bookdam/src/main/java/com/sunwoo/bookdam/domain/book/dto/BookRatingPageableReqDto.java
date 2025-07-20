package com.sunwoo.bookdam.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 평점, 리뷰 목록 조회용 페이징 및 정렬 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookRatingPageableReqDto {

    @Schema(
            description  = "페이지 번호 (1부터, 기본 1)",
            example      = "1",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Min(1)
    private Integer page;

    @Schema(
            description  = "페이지 크기 (최대 100, 기본 20)",
            example      = "20",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Min(1)
    @Max(100)
    private Integer size;

    @Schema(
            description  = "정렬 (예: createdAt,desc 또는 ratingAvg,asc)",
            example      = "createdAt,desc",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Pattern(
            regexp  = "^[a-zA-Z0-9]+,(asc|desc)$",
            message = "sort는 '필드명,asc' 또는 '필드명,desc' 형식이어야 합니다."
    )
    private String sort;

    // 기본값 보장
    public int getPage() {
        return page == null ? 1 : page;
    }

    public int getSize() {
        return size == null ? 20 : size;
    }
}

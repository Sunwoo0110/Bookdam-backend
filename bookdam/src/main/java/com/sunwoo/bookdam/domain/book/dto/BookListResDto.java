package com.sunwoo.bookdam.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

/**
 * 책 리스트 응답 DTO
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookListResDto {
    @Schema(
            description  = "책 ID",
            example      = "1",
            requiredMode = RequiredMode.REQUIRED
    )
    private final Long id;

    @Schema(
            description  = "도서명",
            example      = "토비의 스프링 3.1",
            requiredMode = RequiredMode.REQUIRED
    )
    private final String title;

    @Schema(
            description  = "저자명",
            example      = "이일민",
            requiredMode = RequiredMode.REQUIRED
    )
    private final String author;

    // 평균 평점이 없을 경우 0.0으로 반환
    @Schema(
            description  = "평균 평점 (null인 경우 0.0)",
            example      = "4.5",
            requiredMode = RequiredMode.REQUIRED
    )
    private final double ratingAvg;

    @Schema(
            description  = "평점 등록 건수",
            example      = "200",
            requiredMode = RequiredMode.REQUIRED
    )
    private final long ratingCount;

    @Schema(
            description  = "포스트 등록 건수",
            example      = "200",
            requiredMode = RequiredMode.REQUIRED
    )
    private final long postCount;

    @Schema(
            description  = "표지 이미지 URL",
            example      = "http://example.com/cover.jpg",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private final String coverImage;
}


package com.sunwoo.bookdam.domain.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 책 상세 응답 DTO
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDetailResDto {
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

    @Schema(
            description  = "ISBN",
            example      = "9788960774041",
            requiredMode = RequiredMode.REQUIRED
    )
    private final String isbn;

    @Schema(
            description  = "표지 이미지 URL",
            example      = "http://example.com/cover.jpg",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private final String coverImage;

    @Schema(
            description  = "설명",
            example      = "스프링 핵심 가이드",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private final String description;

    @Schema(
            description  = "평균 평점",
            example      = "4.5",
            requiredMode = RequiredMode.REQUIRED
    )
    private final double ratingAvg;

    @Schema(
            description  = "평점 등록 건수",
            example      = "200",
            requiredMode = RequiredMode.REQUIRED
    )
    private final int ratingCount;

    @Schema(
            description  = "생성일시",
            example      = "2025-07-18T14:30:00",
            requiredMode = RequiredMode.REQUIRED
    )
    @JsonFormat(
            shape   = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private final LocalDateTime createdAt;

    @Schema(
            description  = "수정일시",
            example      = "2025-07-19T09:15:00",
            requiredMode = RequiredMode.REQUIRED
    )
    @JsonFormat(
            shape   = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private final LocalDateTime updatedAt;
}

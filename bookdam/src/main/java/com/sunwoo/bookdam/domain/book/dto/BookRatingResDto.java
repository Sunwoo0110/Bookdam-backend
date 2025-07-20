package com.sunwoo.bookdam.domain.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 책 평점 및 리뷰 응답 DTO
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookRatingResDto {
    @Schema(
            description  = "리뷰 ID",
            example      = "123",
            requiredMode = RequiredMode.REQUIRED
    )
    private final Long id;

    @Schema(
            description  = "평점 (1~5)",
            example      = "4",
            requiredMode = RequiredMode.REQUIRED
    )
    private final Integer rating;

    @Schema(
            description  = "리뷰 내용",
            example      = "정말 재미있게 읽었습니다.",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    private final String review;

    @Schema(
            description  = "리뷰어 사용자 ID",
            example      = "42",
            requiredMode = RequiredMode.REQUIRED
    )
    private final Long userId;

    @Schema(
            description  = "리뷰어 사용자 이름",
            example      = "testuser",
            requiredMode = RequiredMode.REQUIRED)
    private final String username;

    @Schema(
            description  = "리뷰 등록일시",
            example      = "2025-07-18T15:00:00",
            requiredMode = RequiredMode.REQUIRED
    )
    @JsonFormat(
            shape   = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private final LocalDateTime createdAt;

    @Schema(
            description  = "리뷰 수정일시",
            example      = "2025-07-19T09:15:00",
            requiredMode = RequiredMode.REQUIRED
    )
    @JsonFormat(
            shape   = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private final LocalDateTime updatedAt;
}


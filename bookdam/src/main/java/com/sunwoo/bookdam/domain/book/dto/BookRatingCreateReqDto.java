package com.sunwoo.bookdam.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

/**
 * 평점, 리뷰 등록 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookRatingCreateReqDto {

    @Schema(
            description = "평점 (1~5)",
            example = "4",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotNull
    @Min(1)
    @Max(5)
    private Integer rating;

    @Schema(description = "리뷰 내용 (optional, 최대 1000자)", example = "정말 재미있게 읽었습니다.")
    @Size(max = 1000)
    private String review;

    public void setReview(String review) {
        this.review = (review == null ? null : review.trim());
    }
}
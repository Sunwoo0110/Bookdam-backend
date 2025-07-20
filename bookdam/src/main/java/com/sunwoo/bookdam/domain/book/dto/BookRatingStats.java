package com.sunwoo.bookdam.domain.book.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 평점 통계용 DTO
 */
@Getter
@Builder
public class BookRatingStats {
    private final Long count;
    private final Double avg;
}


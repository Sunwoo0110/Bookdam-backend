package com.sunwoo.bookdam.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 책 등록 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor
public class BookCreateReqDto {
    @Schema(description = "도서명 (필수)", example = "토비의 스프링 3.1")
    @NotBlank
    private String title;

    @Schema(description = "저자명 (필수)", example = "이일민")
    @NotBlank
    private String author;

    @Schema(description = "ISBN (필수, 10~13자리 숫자)", example = "9788960774041")
    @NotBlank
    @Pattern(regexp = "^(?:\\d[- ]?){9,13}\\d$")
    private String isbn;

    @Schema(description = "표지 이미지 URL (optional)", example = "http://example.com/cover.jpg")
    private String coverImage;

    @Schema(description = "설명 (optional)", example = "스프링 핵심 가이드")
    private String description;
}


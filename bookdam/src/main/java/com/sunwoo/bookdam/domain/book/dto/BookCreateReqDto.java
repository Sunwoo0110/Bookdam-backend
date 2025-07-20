package com.sunwoo.bookdam.domain.book.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;

/**
 * 책 등록 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookCreateReqDto {
    @Schema(
            description  = "도서명 (필수, 최대 128자)",
            example      = "토비의 스프링 3.1",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(max = 128)
    private String title;

    @Schema(
            description  = "저자명 (필수, 최대 64자)",
            example      = "이일민",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank
    @Size(max = 64)
    private String author;

    @Schema(
            description  = "ISBN (필수, 10자리 또는 13자리)",
            example      = "9788960774041",
            requiredMode = RequiredMode.REQUIRED
    )
    @NotBlank
    @Pattern(
            regexp  = "^(?:\\d{9}[\\dX]|\\d{13})$",
            message = "ISBN은 10자리(마지막 X 허용) 또는 13자리 숫자여야 합니다."
    )
    private String isbn;

    @Schema(
            description  = "표지 이미지 URL (optional)",
            example      = "http://example.com/cover.jpg",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Size(
            max     = 256,
            message = "coverImage URL은 최대 256자까지 입력할 수 있습니다."
    )
    @URL(message = "유효한 URL 형식이어야 합니다.")
    private String coverImage;

    @Schema(
            description  = "설명 (optional, 최대 2000자)",
            example      = "스프링 핵심 가이드",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Size(max = 2000)
    private String description;
}
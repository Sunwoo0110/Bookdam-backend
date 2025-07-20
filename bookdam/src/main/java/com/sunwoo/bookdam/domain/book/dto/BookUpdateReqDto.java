package com.sunwoo.bookdam.domain.book.dto;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.*;
import org.hibernate.validator.constraints.URL;

/**
 * 책 수정 요청 DTO
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BookUpdateReqDto {

    @Schema(
            description  = "도서명 (optional)",
            example      = "토비의 스프링 3.1",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Size(max = 128, message = "title은 최대 128자까지 입력할 수 있습니다.")
    private String title;

    @Schema(
            description  = "저자명 (optional)",
            example      = "이일민",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Size(max = 64, message = "author는 최대 64자까지 입력할 수 있습니다.")
    private String author;

    @Schema(
            description  = "표지 이미지 URL (optional)",
            example      = "http://example.com/cover_updated.jpg",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Size(
            max     = 256,
            message = "coverImage URL은 최대 1000자까지 입력할 수 있습니다."
    )
    @URL(message = "유효한 URL 형식이어야 합니다.")
    private String coverImage;

    @Schema(
            description  = "설명 (optional)",
            example      = "최신 내용 보강",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @Size(
            max = 2000,
            message = "description은 최대 2000자까지 입력할 수 있습니다."
    )
    private String description;

    // 입력 정규화 (trim)
    public void setTitle(String title) {
        this.title = (title == null ? null : title.trim());
    }

    public void setAuthor(String author) {
        this.author = (author == null ? null : author.trim());
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = (coverImage == null ? null : coverImage.trim());
    }

    public void setDescription(String description) {
        this.description = (description == null ? null : description.trim());
    }
}
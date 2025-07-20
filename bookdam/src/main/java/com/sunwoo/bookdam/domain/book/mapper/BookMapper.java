package com.sunwoo.bookdam.domain.book.mapper;

import com.sunwoo.bookdam.domain.book.dto.*;
import com.sunwoo.bookdam.domain.book.entity.BookRatingEntity;
import org.mapstruct.*;

import com.sunwoo.bookdam.domain.book.entity.BookEntity;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface BookMapper {
    // 생성
    @Mapping(target = "ratingAvg", constant = "0.0")
    @Mapping(target = "ratingCount", constant = "0")
    @Mapping(target = "postCount", constant = "0")
    BookEntity toEntity(BookCreateReqDto dto);

    // 수정: dto 의 null 값은 무시하고, non-null만 매핑
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDto(BookUpdateReqDto dto, @MappingTarget BookEntity entity);

    default BookListResDto toListDto(BookEntity e) {
        return BookListResDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .author(e.getAuthor())
                .ratingAvg(e.getRatingAvg())
                .ratingCount(e.getRatingCount())
                .postCount(e.getPostCount())
                .coverImage(e.getCoverImage())
                .build();
    }

    default BookDetailResDto toDetailDto(BookEntity e) {
        return BookDetailResDto.builder()
                .id(e.getId())
                .title(e.getTitle())
                .author(e.getAuthor())
                .isbn(e.getIsbn())
                .coverImage(e.getCoverImage())
                .description(e.getDescription())
                .ratingAvg(e.getRatingAvg())
                .ratingCount(e.getRatingCount())
                .postCount(e.getPostCount())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    default BookRatingResDto toRatingDto(BookRatingEntity e) {
        return BookRatingResDto.builder()
                .id(e.getId())
                .rating(e.getRating())
                .review(e.getReview())
                .userId(e.getUser().getId())
                .username(e.getUser().getUsername())
                .createdAt(e.getCreatedAt())
                .updatedAt(e.getUpdatedAt())
                .build();
    }

    // 생성 직후 한 번만 전체 정규화
    @AfterMapping
    default void normalize(@MappingTarget BookEntity e) {
        e.updateTitle(e.getTitle());
        e.updateAuthor(e.getAuthor());
        e.updateIsbn(e.getIsbn());
        e.updateCoverImage(e.getCoverImage());
        e.updateDescription(e.getDescription());
    }
}

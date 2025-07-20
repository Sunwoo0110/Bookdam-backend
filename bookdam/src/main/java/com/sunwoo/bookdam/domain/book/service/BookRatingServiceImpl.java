package com.sunwoo.bookdam.domain.book.service;

import com.sunwoo.bookdam.domain.book.dto.*;
import com.sunwoo.bookdam.domain.book.entity.BookEntity;
import com.sunwoo.bookdam.domain.book.entity.BookRatingEntity;
import com.sunwoo.bookdam.domain.book.mapper.BookMapper;
import com.sunwoo.bookdam.domain.book.repository.BookRepository;
import com.sunwoo.bookdam.domain.book.repository.BookRatingRepository;
import com.sunwoo.bookdam.domain.user.entity.UserEntity;
import com.sunwoo.bookdam.domain.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookRatingServiceImpl implements BookRatingService {

    private final BookRepository bookRepository;
    private final BookRatingRepository bookRatingRepository;
    private final UserRepository userRepository;
    private final MessageSource messageSource;
    private final BookMapper mapper;

    @Override
    @Transactional
    public BookRatingResDto addRating(Long bookId, BookRatingCreateReqDto req) {
        // 1. Book 조회 (soft‑delete 체크)
        BookEntity book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.book.notfound",
                                new Object[]{bookId},
                                LocaleContextHolder.getLocale())
                ));

        // 2. 현재 로그인 사용자 조회
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();
        UserEntity reviewer = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        messageSource.getMessage("error.user.notfound",
                                new Object[]{username},
                                LocaleContextHolder.getLocale())
                ));

        // 3. 리뷰 저장
        BookRatingEntity saved = bookRatingRepository.save(
                BookRatingEntity.builder()
                        .book(book)
                        .rating(req.getRating())
                        .review(req.getReview())
                        .user(reviewer)
                        .build()
        );

        // 4. 통계 재계산 (count + avg)
        BookRatingStats stats = bookRatingRepository.findStatsByBook(book);
        book.updateRating(stats.getAvg(), stats.getCount());

        // 5. 응답 DTO 매핑
        return mapper.toRatingDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BookRatingResDto> listRatings(Long bookId, BookRatingPageableReqDto req) {
        // 1. Book 존재 확인
        BookEntity book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.book.notfound",
                                new Object[]{bookId},
                                LocaleContextHolder.getLocale())
                ));

        // 2. Pageable 생성
        Pageable p = buildPageable(req);

        // 3. 페이징 조회 및 DTO 변환
        return bookRatingRepository
                .findByBookAndIsDeletedFalse(book, p)
                .map(mapper::toRatingDto);
    }

    // 추가 메소드
    /** 정렬 허용 필드 */
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("createdAt", "rating");

    /** 페이징 생성 */
    private Pageable buildPageable(BookRatingPageableReqDto req) {
        int page = Optional.ofNullable(req.getPage())
                .filter(p -> p > 0)
                .orElse(1) - 1;
        int size = Optional.ofNullable(req.getSize())
                .filter(s -> s > 0)
                .orElse(20);

        Sort.Direction direction = parseDirection(req.getSort());
        String property = parseProperty(req.getSort());

        return PageRequest.of(page, size, Sort.by(direction, property));
    }

    /** sort 파라미터가 "필드,asc|desc" 형태일 때 방향을 파싱, 아니면 DESC */
    private Sort.Direction parseDirection(String sort) {
        if (!StringUtils.hasText(sort)) {
            return Sort.Direction.DESC;
        }
        String[] parts = sort.split(",", 2);
        return "asc".equalsIgnoreCase(parts[1].trim())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
    }

    /** sort 파라미터에서 필드를 파싱하고, 허용된 필드가 아니면 400 오류 */
    private String parseProperty(String sort) {
        if (!StringUtils.hasText(sort)) {
            return "createdAt";
        }
        String prop = sort.split(",", 2)[0].trim();
        if (!ALLOWED_SORT_FIELDS.contains(prop)) {
            throw new IllegalArgumentException("지원하지 않는 sort 필드: " + prop);
        }
        return prop;
    }
}

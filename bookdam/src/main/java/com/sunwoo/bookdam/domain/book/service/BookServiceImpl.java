package com.sunwoo.bookdam.domain.book.service;

import com.sunwoo.bookdam.domain.book.dto.*;
import com.sunwoo.bookdam.domain.book.entity.BookEntity;
import com.sunwoo.bookdam.domain.book.mapper.BookMapper;
import com.sunwoo.bookdam.domain.book.repository.BookRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper mapper;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#req.page + '-' + #req.size + '-' + #req.sort")
    public Page<BookListResDto> listBooks(BookSearchReqDto req) {
        Pageable p = buildPageable(req);
        return bookRepository.findAllByIsDeletedFalse(p)
                .map(mapper::toListDto);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "book", key = "#id")
    public BookDetailResDto getBook(Long id) {
        BookEntity e = bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                    messageSource.getMessage("error.book.notfound",
                            new Object[]{id},
                            LocaleContextHolder.getLocale())
                ));
        return mapper.toDetailDto(e);
    }

    @Override
    @Transactional
    public BookDetailResDto createBook(BookCreateReqDto req) {
        if (bookRepository.findByIsbnAndIsDeletedFalse(req.getIsbn()).isPresent()) {
            throw new IllegalArgumentException(
                    messageSource.getMessage("error.book.isPresent",
                            new Object[]{req.getIsbn()},
                            LocaleContextHolder.getLocale())
            );
        }
        BookEntity saved = bookRepository.save(mapper.toEntity(req));
        return mapper.toDetailDto(saved);
    }

    @Override
    @Transactional
    public BookDetailResDto updateBook(Long id, BookUpdateReqDto reqDto) {
        BookEntity e = bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.book.notfound",
                                new Object[]{id},
                                LocaleContextHolder.getLocale())
                ));
        mapper.updateEntityFromDto(reqDto, e);
        return mapper.toDetailDto(e);
    }

    @Override
    @Transactional
    public void softDeleteBook(Long id) {
        BookEntity e = bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("error.book.notfound",
                                new Object[]{id},
                                LocaleContextHolder.getLocale())
                ));
        e.softDelete();
    }

    @Override
    @Transactional
    public void hardDeleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new EntityNotFoundException(messageSource.getMessage("error.book.notfound",
                    new Object[]{id},
                    LocaleContextHolder.getLocale())
            );
        }
        bookRepository.deleteById(id);
    }

    // 추가 메소드

    /** 정렬 허용 필드 */
    private static final Set<String> ALLOWED_SORT_FIELDS =
            Set.of("createdAt", "postCount", "ratingAvg");

    /** 페이징 생성 */
    private Pageable buildPageable(BookSearchReqDto req) {
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

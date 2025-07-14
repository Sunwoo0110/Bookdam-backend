package com.sunwoo.bookdam.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "book")
public class BookEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false, length = 128)
    private String title;

    @Column(name = "author", nullable = false, length = 64)
    private String author;

    @Column(name = "isbn", nullable = false, unique = true, length = 32)
    private String isbn;

    @Column(name = "cover_image", length = 256)
    private String coverImage;

    // 1~5점
    @Column(name = "rating_count", nullable = false)
    private int ratingCount = 0;

    // Nullable True -> 아직 평점 등록 없음
    @Column(name = "rating_avg", nullable = true)
    private Double ratingAvg;

    @Lob
    @Column(name = "description", columnDefinition = "text")
    private String description;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    // 공백 제거 및 소문자로 변경
    public void setIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.trim().toLowerCase();
    }

    // DB에 저장 및 수정될 때마다 자동으로 정규화
    @PrePersist
    @PreUpdate
    public void normalize() {
        if (this.isbn != null) this.isbn = this.isbn.trim();
    }

    // Builder 커스텀 (JPA 자동 관리 칼럼 제외)
    @Builder
    public BookEntity(String title, String author, String isbn, String coverImage,
                      int ratingCount, Double ratingAvg, String description) {
        this.title = title;
        this.author = author;
        this.isbn = isbn == null ? null : isbn.trim().toLowerCase();
        this.coverImage = coverImage;
        this.ratingCount = ratingCount;
        this.ratingAvg = ratingAvg;
        this.description = description;
        // createdAt/updatedAt/isDeleted 등은 JPA가 자동 관리
    }

    // ID 만 equals/hashcode 에서 사용하도록 Override
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        // 프록시도 클래스 동등성 비교
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BookEntity that = (BookEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        // 항상 클래스 단위 해시코드
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }

}
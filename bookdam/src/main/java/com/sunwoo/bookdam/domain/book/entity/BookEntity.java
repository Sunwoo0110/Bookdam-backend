package com.sunwoo.bookdam.domain.book.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "book")
@DynamicInsert
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

    // 평점 개수
    @Column(name = "rating_count", nullable = false)
    @ColumnDefault("0")
    private long ratingCount = 0;

    // 평균 평점
    @Column(name="rating_avg", nullable=false)
    @ColumnDefault("0.0")
    private double ratingAvg = 0.0;

    // 포스트 개수
    @Column(name = "post_count", nullable = false)
    @ColumnDefault("0")
    private long postCount = 0;

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

    public void updateTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public void updateAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public void updateIsbn(String isbn) {
        this.isbn = isbn == null ? null : isbn.replaceAll("[- ]", "").toLowerCase();
    }

    public void updateCoverImage(String coverImage) {
        this.coverImage = coverImage == null ? null : coverImage.trim();
    }

    public void updateDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public void softDelete() {
        this.isDeleted = true;
    }

    public void updateRating(Double avg, Long count) {
        this.ratingAvg = avg;
        this.ratingCount = count;
    }

    private static String normalizeString(String str) {
        return (str == null || str.trim().isEmpty()) ? null : str.trim();
    }

    // DB에 저장 및 수정될 때마다 자동으로 정규화
    @PrePersist @PreUpdate
    private void normalize() {
        this.title = normalizeString(title);
        this.author =  normalizeString(author);
        this.isbn =  normalizeString(isbn).toLowerCase();
    }

    // Builder 커스텀 (JPA 자동 관리 칼럼 제외)
    @Builder
    public BookEntity(String title, String author, String isbn, String coverImage,
                      int ratingCount, Double ratingAvg, int postCount, String description) {
        this.title = normalizeString(title);
        this.author =  normalizeString(author);
        this.isbn =  normalizeString(isbn).toLowerCase();
        this.coverImage = coverImage;
        this.ratingCount = ratingCount;
        this.ratingAvg = ratingAvg;
        this.postCount = postCount;
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
package com.sunwoo.bookdam.domain.book.entity;

import com.sunwoo.bookdam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "book_rating")
public class BookRatingEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "rating", nullable = false)
    private int rating; // 1~5점

    @Lob
    @Column(name = "review", columnDefinition = "text")
    private String review;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public void softDelete() { this.isDeleted = true; }

    public void updateRating(int rating) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1~5");
        this.rating = rating;
    }

    // DB 저장/수정 전 정규화
    @PrePersist
    @PreUpdate
    public void normalize() {
        this.review = (review == null || review.trim().isEmpty()) ? null : review.trim();
    }

    // Builder 커스텀 (JPA 자동 관리 칼럼 제외)
    @Builder
    public BookRatingEntity(int rating, String review, BookEntity book, UserEntity user) {
        if (rating < 1 || rating > 5) throw new IllegalArgumentException("Rating must be 1~5");
        this.rating = rating;
        this.review = (review == null || review.trim().isEmpty()) ? null : review.trim();
        this.book = book;
        this.user = user;
        // createdAt, updatedAt, isDeleted, id 등은 JPA 관리
    }

    // ID 만 equals/hashcode 에서 사용하도록 Override
    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy
                ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass()
                : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass()
                : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        BookRatingEntity that = (BookRatingEntity) o;
        return getId() != null && getId().equals(that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

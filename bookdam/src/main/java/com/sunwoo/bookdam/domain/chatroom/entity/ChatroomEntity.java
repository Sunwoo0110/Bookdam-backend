package com.sunwoo.bookdam.domain.chatroom.entity;

import com.sunwoo.bookdam.domain.book.entity.BookEntity;
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
@Table(name = "chatroom")
public class ChatroomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 16, nullable = false)
    private ChatroomType type;

    @Column(length = 64, nullable = false)
    private String title;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity book;

    public enum ChatroomType {
        PRIVATE, OPEN
    }

    // title 정규화
    @PrePersist @PreUpdate
    private void normalize() {
        this.title = (title == null || title.trim().isEmpty()) ? null : title.trim();
    }

    // Builder 커스텀 (자동 관리 필드 제외)
    @Builder
    public ChatroomEntity(ChatroomType type, String title, BookEntity book) {
        this.type = type;
        this.title = (title == null || title.trim().isEmpty()) ? null : title.trim();
        this.book = book;
        // createdAt, updatedAt, isDeleted, id 등은 JPA가 자동 관리
    }

    // ID만 equals/hashcode
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
        ChatroomEntity that = (ChatroomEntity) o;
        return getId() != null && getId().equals(that.getId());
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

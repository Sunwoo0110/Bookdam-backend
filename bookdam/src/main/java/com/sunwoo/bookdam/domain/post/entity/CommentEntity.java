package com.sunwoo.bookdam.domain.post.entity;

import com.sunwoo.bookdam.domain.post.entity.PostEntity;
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
@Table(name = "comment")
public class CommentEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @Column(nullable = false)
    private int depth = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private CommentEntity parent;

    // Soft delete 도메인 메서드
    public void softDelete() { this.isDeleted = true; }

    // 댓글 내용 정규화 (공백만 입력시 null)
    @PrePersist @PreUpdate
    private void normalize() {
        this.content = (content == null || content.trim().isEmpty()) ? null : content.trim();
    }

    // Builder 커스텀(JPA 자동 관리 칼럼 제외)
    @Builder
    public CommentEntity(String content, int depth, PostEntity post, UserEntity user, CommentEntity parent) {
        this.content = (content == null || content.trim().isEmpty()) ? null : content.trim();
        this.depth = depth;
        this.post = post;
        this.user = user;
        this.parent = parent;
        // 나머지는 JPA 자동 관리
    }

    // equals/hashCode 오버라이드(id 기준)
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
        CommentEntity that = (CommentEntity) o;
        return getId() != null && getId().equals(that.getId());
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

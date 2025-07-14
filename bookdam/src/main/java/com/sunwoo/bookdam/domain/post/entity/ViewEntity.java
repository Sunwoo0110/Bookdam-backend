package com.sunwoo.bookdam.domain.post.entity;

import com.sunwoo.bookdam.domain.post.entity.PostEntity;
import com.sunwoo.bookdam.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "view")
public class ViewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Column(name = "viewed_at", nullable = false, updatable = false)
    private LocalDateTime viewedAt;

    @Column(name = "ip_address", length = 64)
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user; // 비회원이면 null

    // ipAddress 정규화(공백 제거)
    @PrePersist @PreUpdate
    private void normalize() {
        this.ipAddress = (ipAddress == null || ipAddress.trim().isEmpty()) ? null : ipAddress.trim();
    }

    @Builder
    public ViewEntity(String ipAddress, PostEntity post, UserEntity user) {
        this.ipAddress = (ipAddress == null || ipAddress.trim().isEmpty()) ? null : ipAddress.trim();
        this.post = post;
        this.user = user;
        // viewedAt, id 등은 JPA 자동 관리
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
        ViewEntity that = (ViewEntity) o;
        return getId() != null && getId().equals(that.getId());
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

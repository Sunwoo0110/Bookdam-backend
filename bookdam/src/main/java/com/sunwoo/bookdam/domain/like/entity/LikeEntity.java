package com.sunwoo.bookdam.domain.like.entity;

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
@Table(name = "like", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "target_type", "target_id"})
})
public class LikeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "target_type", length = 16, nullable = false)
    private TargetType targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public enum TargetType {
        POST, COMMENT
    }

    private static TargetType parseTargetType(String type) {
        if (type == null || type.trim().isEmpty()) return TargetType.POST; // 기본값 필요시
        try {
            return TargetType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return TargetType.POST; // 잘못된 값 들어오면 기본값/에러처리
        }
    }

    @Builder
    public LikeEntity(String targetType, Long targetId, UserEntity user) {
        this.targetType = parseTargetType(targetType);
        this.targetId = targetId;
        this.user = user;
        // createdAt, updatedAt, id는 JPA가 관리
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
       LikeEntity that = (LikeEntity) o;
        return getId() != null && getId().equals(that.getId());
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

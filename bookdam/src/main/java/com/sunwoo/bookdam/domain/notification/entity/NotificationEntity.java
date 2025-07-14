package com.sunwoo.bookdam.domain.notification.entity;

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
@Table(name = "notification")
public class NotificationEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 32, nullable = false)
    private NotificationType type;

    @Column(length = 256, nullable = false)
    private String message;

    @Column(length = 256)
    private String link;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead = false;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    private Boolean isDeleted = false;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    public enum NotificationType { NEW_COMMENT, NEW_LIKE, NEW_MESSAGE }

    private static NotificationType parseType(String type) {
        if (type == null || type.trim().isEmpty()) return NotificationType.NEW_MESSAGE;
        try {
            return NotificationType.valueOf(type.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return NotificationType.NEW_MESSAGE;
        }
    }

    // 읽음 처리 도메인 메서드
    public void markRead() { this.isRead = true; }

    public void softDelete() { this.isDeleted = true; }

    private static String normalizeString(String str) {
        return (str == null || str.trim().isEmpty()) ? null : str.trim();
    }

    // 저장/수정 시 메시지 트림
    @PrePersist @PreUpdate
    private void normalize() {
        this.message = normalizeString(message);
        this.link = normalizeString(link);
    }

    // Builder 커스텀
    @Builder
    public NotificationEntity(String type, String message, String link, UserEntity user) {
        this.type = parseType(type);
        this.message = normalizeString(message);
        this.link = (normalizeString(link));
        this.user = user;
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
        NotificationEntity that = (NotificationEntity) o;
        return getId() != null && getId().equals(that.getId());
    }
    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

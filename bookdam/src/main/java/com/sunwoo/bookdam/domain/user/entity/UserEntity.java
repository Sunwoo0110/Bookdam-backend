package com.sunwoo.bookdam.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "user")
public class UserEntity implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 32)
    private String username;

    @Column(name = "password", nullable = false, length = 128)
    private String password;

    @Column(name = "nickname", nullable = false, length = 32)
    private String nickname;

    @Column(name = "email", nullable = false, unique = true, length = 128)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 16)
    private UserRole role;

    @Column(name = "profile_image", length = 256)
    private String profileImage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public boolean isActive() {
        return deletedAt == null;
    }

    public enum UserRole {
        ROLE_USER, ROLE_ADMIN
    }

    // 중복 방지를 위한 공백 제거 및 소문자로 변경
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim().toLowerCase();
    }
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim().toLowerCase();
    }

    // DB에 저장 및 수정될 때마다 자동으로 정규화
    @PrePersist
    @PreUpdate
    public void normalize() {
        if (this.email != null) this.email = this.email.trim().toLowerCase();
        if (this.username != null) this.username = this.username.trim().toLowerCase();
    }

    // Spring Security UserDetail Override
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(role.name()));
    }

    @Override public String getPassword() { return password; }
    @Override public String getUsername() { return username; }

    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return deletedAt == null; }

}

package com.sunwoo.bookdam.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
@Table(name = "user")
public class UserEntity {
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

    @Column(name = "role", nullable = false, length = 16)
    private String role;

    @Column(name = "profile_image", length = 256)
    private String profileImage;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}

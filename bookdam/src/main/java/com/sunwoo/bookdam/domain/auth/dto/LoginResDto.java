package com.sunwoo.bookdam.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class LoginResDto {
    @Schema(description = "token",  example = "")
    @NotNull
    private String accessToken;

    @Schema(description = "사용자 아이디", example = "1")
    @NotNull
    private Long id;

    @Schema(description = "사용자 이름", example = "testuser")
    @NotNull
    private String username;

    @Schema(description = "사용자 role", example = "ROLE_USER")
    @NotNull
    private String role;

    @Schema(description = "expire in", example = "")
    @NotNull
    private String expireIn;

    @Schema(description = "refresh token", example = "")
    @NotNull
    private String refreshToken;

    @Schema(description = "refresh expire in", example = "")
    @NotNull
    private String refreshExpireIn;

    @Builder
    public LoginResDto(@NotNull String accessToken, @NotNull Long id, @NotNull String username, @NotNull String role, @NotNull String expireIn, @NotNull String refreshToken) {
        this.accessToken = accessToken;
        this.id = id;
        this.username = username;
        this.role = role;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
    }
}

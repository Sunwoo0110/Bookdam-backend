package com.sunwoo.bookdam.domain.user.dto;

import lombok.Builder;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data
public class UserInfoResDto {

    @Schema(description = "사용자 아이디", example = "1")
    @NotNull
    private Long id;

    @Schema(description = "사용자 이름", example = "testuser")
    @NotNull
    private String username;

    @Schema(description = "사용자 닉네임", example = "sunnnnn")
    @NotNull
    private String nickname;

    @Schema(description = "사용자 이메일", example = "sunnnnn@gmail.com")
    @NotNull
    private String email;

    @Schema(description = "사용자 사진", example = "")
    private String profileImage;

    @Schema(description = "사용자 role", example = "ROLE_USER")
    @NotNull
    private String role;

    @Builder
    public UserInfoResDto(@NotNull Long id, @NotNull String username, @NotNull String nickname, @NotNull String email, String profileImage, @NotNull String role) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.profileImage = profileImage;
        this.role = role;
    }
}

package com.sunwoo.bookdam.domain.auth.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class SignupReqDto {
    @Schema(description = "사용자 이름",  example = "testuser")
    @NotNull
    private String username;

    @Schema(description = "사용자 비밀번호",  example = "testuser0000")
    @NotNull
    private String password;

    @Schema(description = "사용자 닉네임",  example = "sunnnnnn")
    @NotNull
    private String nickname;

    @Schema(description = "사용자 이메일",  example = "testuser@gmail.com")
    @NotNull
    private String email;
}
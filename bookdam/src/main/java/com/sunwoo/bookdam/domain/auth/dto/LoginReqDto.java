package com.sunwoo.bookdam.domain.auth.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class LoginReqDto {
    @Schema(description = "사용자 이름",  example = "testuser")
    @NotNull
    private String username;

    @Schema(description = "사용자 비밀번호",  example = "testuser0000")
    @NotNull
    private String password;
}
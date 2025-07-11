package com.sunwoo.bookdam.domain.auth.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class TokenResDto {
    @Schema(description = "Token",  example = "")
    @NotNull
    private String accessToken;

    @Schema(description = "Token",  example = "")
    @NotNull
    private String refreshToken;

    @Builder
    public TokenResDto(@NotNull String accessToken, @NotNull String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}

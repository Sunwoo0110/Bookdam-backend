package com.sunwoo.bookdam.domain.auth.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Data
public class RefreshReqDto {
    @Schema(description = "token",  example = "")
    @NotNull
    private String refreshToken;
}
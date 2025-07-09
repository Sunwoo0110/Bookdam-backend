package com.sunwoo.bookdam.common.model.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonIdResult {

    @Schema(description = "아이디", example = "1", required = true)
    private final Long id;

}


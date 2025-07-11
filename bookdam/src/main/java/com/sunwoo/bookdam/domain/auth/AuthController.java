package com.sunwoo.bookdam.domain.auth;

import com.sunwoo.bookdam.common.model.response.BaseResponse;
import com.sunwoo.bookdam.common.model.response.CommonResult;
import com.sunwoo.bookdam.common.security.JwtTokenProvider;
import com.sunwoo.bookdam.domain.auth.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.Operation;

@Slf4j
@RestController
@Tag(name = "01. Auth", description = "인증 관련 API")
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final BaseResponse baseResponse;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "회원가입")
    @RequestBody(description = "새로운 유저 등록 정보", required = true)
    public CommonResult<String> signup(@RequestBody @Valid SignupReqDto reqDto) {
        return baseResponse.getContentResult(authService.signup(reqDto));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "로그인")
    @RequestBody(description = "로그인 정보", required = true)
    public CommonResult<LoginResDto> login(@RequestBody @Valid LoginReqDto reqDto) {
        return baseResponse.getContentResult(authService.login(reqDto));
    }

    @PostMapping("/refresh")
    public CommonResult<TokenResDto> refresh(@RequestBody @Valid RefreshReqDto reqDto) {
        return baseResponse.getContentResult(authService.refresh(reqDto));
    }
}

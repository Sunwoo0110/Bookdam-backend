package com.sunwoo.bookdam.domain.user;

import com.sunwoo.bookdam.common.model.response.BaseResponse;
import com.sunwoo.bookdam.common.model.response.CommonResult;
import com.sunwoo.bookdam.domain.user.dto.UserInfoResDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Tag(name = "02. User", description = "유저 관련 API")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final BaseResponse baseResponse;
    private final UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "userID로 정보 조회", description = "userID로 정보 조회")
    public CommonResult<UserInfoResDto> getUserProfileById(@PathVariable Long userId) {
        return baseResponse.getContentResult(userService.getUserProfileById(userId));
    }

    // 프로필 페이지
    // 토큰에서 꺼낸 userName 기반 조회
    @GetMapping("/profile")
    @Operation(summary = "userName으로 정보 조회", description = "Profile 페이지: userName으로 정보 조회")
    public CommonResult<UserInfoResDto> getUserProfileByUsername(Authentication authentication) {
        return baseResponse.getContentResult(userService.getUserProfileByUsername(authentication.getName()));
    }
}
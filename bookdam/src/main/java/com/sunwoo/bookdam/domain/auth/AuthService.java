package com.sunwoo.bookdam.domain.auth;

import com.sunwoo.bookdam.common.security.JwtTokenProvider;
import com.sunwoo.bookdam.domain.auth.dto.*;
import com.sunwoo.bookdam.domain.user.entity.UserEntity;
import com.sunwoo.bookdam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public String signup(SignupReqDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 아이디");
        if (userRepository.findByEmail(dto.getEmail()).isPresent())
            throw new IllegalArgumentException("이미 존재하는 이메일");

        UserEntity user = userRepository.save(UserEntity.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickname(dto.getNickname())
                .email(dto.getEmail())
                .role("ROLE_USER")
                .build());

        return user.getUsername();
    }

    public LoginResDto login(LoginReqDto dto) {
        UserEntity user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("비밀번호 틀림");

        return LoginResDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole())
                .accessToken(jwtTokenProvider.createToken(user.getUsername(), user.getRole(), false))
                .refreshToken(jwtTokenProvider.createToken(user.getUsername(), user.getRole(), true))
                .build();
    }

    public TokenResDto refresh(RefreshReqDto reqDto) {
        if (!jwtTokenProvider.validateToken(reqDto.getRefreshToken()))
            throw new RuntimeException("유효하지 않은 토큰");
        String username = jwtTokenProvider.getUsername(reqDto.getRefreshToken());
        String role = jwtTokenProvider.getUserRole(reqDto.getRefreshToken());
        String accessToken = jwtTokenProvider.createToken(username, role, false);

        return TokenResDto.builder()
                .accessToken(accessToken)
                .refreshToken(reqDto.getRefreshToken())
                .build();
    }
}
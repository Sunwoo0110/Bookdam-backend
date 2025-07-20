package com.sunwoo.bookdam.domain.user;

import com.sunwoo.bookdam.domain.user.dto.UserInfoResDto;
import com.sunwoo.bookdam.domain.user.entity.UserEntity;
import com.sunwoo.bookdam.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserInfoResDto getUserProfileById(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        return UserInfoResDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .role(String.valueOf(user.getRole()))
                .build();
    }

    public UserInfoResDto getUserProfileByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));
        return UserInfoResDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .role(String.valueOf(user.getRole()))
                .build();
    }

}


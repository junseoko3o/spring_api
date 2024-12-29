package com.spring.api.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpUserResponseDto {
    private final Long id;
    private final String email;

    public static SignUpUserResponseDto signUpUserResponseDto(User user) {
        return SignUpUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}

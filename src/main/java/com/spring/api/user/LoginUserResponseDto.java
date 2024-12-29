package com.spring.api.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserResponseDto {
    private final Long id;
    private final String email;

    public static LoginUserResponseDto loginUserResponseDto(User user) {
        return LoginUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .build();
    }
}

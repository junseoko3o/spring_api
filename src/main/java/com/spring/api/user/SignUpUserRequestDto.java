package com.spring.api.user;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SignUpUserRequestDto {
    @Email
    private String email;

    private String password;
}

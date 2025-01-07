package com.spring.api.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SignUpUserRequestDto {
    @Email(message = "email 형식 아님")
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{8,16}$",
            message = "비밀번호는 숫자, 문자, 특수문자 1개 이상 ")
    private String password;
}

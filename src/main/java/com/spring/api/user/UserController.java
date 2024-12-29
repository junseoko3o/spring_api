package com.spring.api.user;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@Validated @RequestBody SignUpUserRequestDto signUpUserRequestDto) {
        return ResponseEntity.ok(userService.saveUser(signUpUserRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<SignUpUserResponseDto> login(@Validated @RequestBody SignUpUserRequestDto signUpUserRequestDto, HttpServletRequest httpServletRequest) {
        User user = userService.loginUser(signUpUserRequestDto);
        httpServletRequest.getSession().setAttribute("user", user);
        httpServletRequest.getSession().setMaxInactiveInterval(3600);
        return ResponseEntity.ok(SignUpUserResponseDto.signUpUserResponseDto(user));
    }

    @GetMapping("/check-session")
    public ResponseEntity<Object> checkSession(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
    }
}
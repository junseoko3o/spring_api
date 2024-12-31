package com.spring.api.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Value("${server.servlet.session.cookie.max-age}")
    private int maxAge;

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
        SignUpUserResponseDto signUpUserResponseDto = SignUpUserResponseDto.signUpUserResponseDto(user);
        HttpSession existingSession = httpServletRequest.getSession(false);
        if (existingSession != null) {
            Long existingUserId = (Long) existingSession.getAttribute("userId");
            if (existingUserId != null && existingUserId.equals(user.getId())) {
                throw new IllegalStateException("이미 로그인된 사용자가 있습니다.");
            }
        }
        httpServletRequest.getSession().setAttribute("userId", user.getId());
        httpServletRequest.getSession().setAttribute("email", user.getEmail());
        httpServletRequest.getSession().setMaxInactiveInterval(maxAge);
        return ResponseEntity.ok(signUpUserResponseDto);
    }

    @GetMapping("/check-session")
    public ResponseEntity<LoginUserResponseDto> checkSession(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        String email = (String) request.getSession().getAttribute("email");
        User findByUer = userService.findByEmail(email);
        return ResponseEntity.ok(LoginUserResponseDto.loginUserResponseDto(findByUer));
    }
}
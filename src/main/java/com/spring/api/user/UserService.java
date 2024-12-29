package com.spring.api.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User saveUser(SignUpUserRequestDto signUpUserRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        User user = User.builder()
                .email(signUpUserRequestDto.getEmail())
                .password(encoder.encode(signUpUserRequestDto.getPassword()))
                .build();
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User loginUser(SignUpUserRequestDto signUpUserRequestDto) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        User user = findByEmail(signUpUserRequestDto.getEmail());
        encoder.matches(signUpUserRequestDto.getPassword(), user.getPassword());
        return user;
    }
}

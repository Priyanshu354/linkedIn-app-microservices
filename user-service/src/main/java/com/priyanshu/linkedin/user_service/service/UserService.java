package com.priyanshu.linkedin.user_service.service;

import com.priyanshu.linkedin.user_service.dto.LoginOrSignupResponse;
import com.priyanshu.linkedin.user_service.dto.LoginRequestDto;
import com.priyanshu.linkedin.user_service.dto.SignupRequestDto;
import com.priyanshu.linkedin.user_service.entity.User;
import com.priyanshu.linkedin.user_service.event.UserCreatedEvent;
import com.priyanshu.linkedin.user_service.exception.BadRequestException;
import com.priyanshu.linkedin.user_service.repository.UserRepository;
import com.priyanshu.linkedin.user_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    private final KafkaTemplate<Long, UserCreatedEvent> kafkaTemplate;

    public LoginOrSignupResponse login(LoginRequestDto loginRequestDto) {

        User user = userRepository
                .findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new BadRequestException("User is not registered"));

        boolean isPasswordValid = BCrypt.checkpw(
                loginRequestDto.getPassword(),
                user.getPassword()
        );

        if (!isPasswordValid) {
            throw new BadRequestException("Invalid password");
        }

        return buildAuthResponse(user);
    }

    public LoginOrSignupResponse signup(SignupRequestDto signupRequestDto) {

        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new BadRequestException("User is already registered");
        }

        User user = modelMapper.map(signupRequestDto, User.class);

        String hashedPassword = BCrypt.hashpw(
                signupRequestDto.getPassword(),
                BCrypt.gensalt()
        );

        user.setPassword(hashedPassword);

        user = userRepository.save(user);

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        userCreatedEvent.setName(user.getName());
        userCreatedEvent.setUserId(user.getId());

        kafkaTemplate.send("user-created-topic", userCreatedEvent);

        return buildAuthResponse(user);
    }

    private LoginOrSignupResponse buildAuthResponse(User user) {

        LoginOrSignupResponse response = new LoginOrSignupResponse();

        response.setUserId(user.getId());
        response.setAccessToken(jwtUtil.generateAccessToken(user));

        return response;
    }
}
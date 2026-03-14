package com.priyanshu.linkedin.user_service.controller;

import com.priyanshu.linkedin.user_service.dto.LoginOrSignupResponse;
import com.priyanshu.linkedin.user_service.dto.LoginRequestDto;
import com.priyanshu.linkedin.user_service.dto.SignupRequestDto;
import com.priyanshu.linkedin.user_service.dto.UserProfileResponse;
import com.priyanshu.linkedin.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    ResponseEntity<LoginOrSignupResponse> login(@RequestBody LoginRequestDto loginRequestDto){
        LoginOrSignupResponse user = userService.login(loginRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/signup")
    ResponseEntity<LoginOrSignupResponse> signup(@RequestBody SignupRequestDto signupRequestDto){
        LoginOrSignupResponse user = userService.signup(signupRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }


}

package com.groupware.wimir.controller;

import com.groupware.wimir.dto.MemberRequestDto;
import com.groupware.wimir.dto.MemberResponseDto;
import com.groupware.wimir.dto.TokenDto;
import com.groupware.wimir.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

//    @PostMapping("/signup")
//    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
//        return ResponseEntity.ok(authService.signup(requestDto));
//    }

    @PostMapping("/admin/signup")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.signup(requestDto));
    }




    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
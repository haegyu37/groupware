package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.*;
import com.groupware.wimir.entity.Authority;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.jwt.TokenStatus;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final DocumentService documentService;


    //로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto) {
        Member member = memberRepository.findByNo(requestDto.getNo())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        // 계정이 차단된 경우 토큰 발급하지 않고 접속 차단 응답 반환
        if (member.getAuthority() == Authority.BLOCK) {
            log.info("접속이 차단된 계정입니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (member.getAuthority() == Authority.DELETE) {
            log.info("삭제된 계정입니다.");
            return ResponseEntity.status(HttpStatus.GONE).build();
        }

        return ResponseEntity.ok(authService.login(requestDto));
    }


    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 리프레시 토큰을 저장하는 쿠키를 제거합니다.
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0); // 쿠키 만료시간을 0으로 설정하여 삭제
        refreshTokenCookie.setPath("/"); // 쿠키의 경로 설정 (로그아웃 처리와 같은 경로로 설정)
        response.addCookie(refreshTokenCookie); // 응답 헤더에 쿠키 추가

        log.info("로그아웃이 성공적으로 처리되었습니다.");

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }

    // 토큰 갱신
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return ResponseEntity.ok(authService.refresh(tokenRequestDTO));
    }


}
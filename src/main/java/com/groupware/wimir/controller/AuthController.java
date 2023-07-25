package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.DTO.TokenDTO;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    //직원 등록
    @PostMapping("/admin/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto) {

        return ResponseEntity.ok(authService.signup(requestDto));
    }

    //직원 목록
    @GetMapping("/admin/members")
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    //직원 삭제
    @DeleteMapping("/admin/members/{memberId}")
    public ResponseEntity<String> deleteMember(@PathVariable Long memberId) {
        try {
            memberRepository.deleteById(memberId);
            return ResponseEntity.ok("회원 정보가 삭제되었습니다.");
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //로그인
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        // 쿠키 삭제를 위해 refreshToken 쿠키 생성
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0); // 쿠키 만료시간을 0으로 설정하여 삭제
        refreshTokenCookie.setPath("/"); // 쿠키의 경로 설정 (반드시 로그아웃 처리와 같은 경로로 설정)
        response.addCookie(refreshTokenCookie); // 응답 헤더에 쿠키 추가

        // 서버에서 리프레시 토큰과 연관된 정보를 초기화하는 로직 추가 (예: 데이터베이스 등)
        // ...

        // 로그아웃 성공시 메시지 반환
        return ResponseEntity.ok("로그아웃 되었습니다.");


    }
}
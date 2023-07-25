package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.*;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    //@Autowired
    private final AuthService authService;
    // @Autowired
    private final MemberRepository memberRepository;

    private final MemberService memberService;

//    @PostMapping("/signup")
//    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
//        return ResponseEntity.ok(authService.signup(requestDto));
//    }

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
    @DeleteMapping("/admin/members/{memberNo}")
    public ResponseEntity<String> deleteMemberByNo(@PathVariable String memberNo) {
        try {
            Optional<Member> memberOptional = memberRepository.findByNo(memberNo);
            if (memberOptional.isPresent()) {
                memberRepository.delete(memberOptional.get());
                return ResponseEntity.ok("회원 정보가 삭제되었습니다.");
            } else {
                return ResponseEntity.notFound().build();
            }
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
        // 리프레시 토큰을 저장하는 쿠키를 제거합니다.
        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setMaxAge(0); // 쿠키 만료시간을 0으로 설정하여 삭제
        refreshTokenCookie.setPath("/"); // 쿠키의 경로 설정 (반드시 로그아웃 처리와 같은 경로로 설정)
        response.addCookie(refreshTokenCookie); // 응답 헤더에 쿠키 추가

        log.info("로그아웃이 성공적으로 처리되었습니다.");

        return ResponseEntity.ok("로그아웃 되었습니다.");
    }
    // 토큰 갱신
    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@RequestBody TokenRequestDTO tokenRequestDTO) {
        return ResponseEntity.ok(authService.refresh(tokenRequestDTO));
    }

    @GetMapping("/admin/members/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
        return ResponseEntity.ok(memberResponseDTO);
    }

    @PostMapping("/admin/members/{id}/password")
    public ResponseEntity<MemberResponseDTO> changeUserPasswordByAdmin(@PathVariable Long id, @RequestBody ResetPasswordDTO newPasswordDto) {
        MemberResponseDTO updatedUser = memberService.changeUserPasswordByAdmin(id, newPasswordDto.getNewPassword());
        return ResponseEntity.ok(updatedUser);
    }

}
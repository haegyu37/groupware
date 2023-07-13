package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.DTO.TokenDTO;
import com.groupware.wimir.dto.MemberResponseDTO;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
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

    @PostMapping("/admin/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto) {

        return ResponseEntity.ok(authService.signup(requestDto));
    }
    @GetMapping("/admin/members")
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }
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


    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
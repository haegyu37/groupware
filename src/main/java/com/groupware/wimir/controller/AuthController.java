package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.*;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

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

    //해당 사원 정보보기
    @GetMapping("/admin/members/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
        return ResponseEntity.ok(memberResponseDTO);
    }

    //해당 사원 비밀번호변경
    @PostMapping("/admin/members/{id}/password")
    public ResponseEntity<MemberResponseDTO> changeUserPasswordByAdmin(@PathVariable Long id, @RequestBody ResetPasswordDTO newPasswordDto) {
        MemberResponseDTO updatedUser = memberService.changeUserPasswordByAdmin(id, newPasswordDto.getNewPassword());
        return ResponseEntity.ok(updatedUser);
    }

    //해당 사원 사진등록
    @PostMapping("/admin/members/{memberId}/profile-image")
    public ResponseEntity<String> uploadProfileImageForMember(
            @PathVariable Long memberId,
            @RequestParam("image") MultipartFile image
    ) {
        try {
            // 이미지를 저장할 디렉토리 경로 지정
            String uploadDir = "src/main/resources/static/images"; //

            // 이미지 파일 이름 생성 (예시: "profile_12345.jpg")
            String fileName = "profile_" + memberId + "." + FilenameUtils.getExtension(image.getOriginalFilename());

            // 이미지를 지정된 경로에 저장
            File file = new File(uploadDir + "/" + fileName);
            FileUtils.writeByteArrayToFile(file, image.getBytes());

            // Member 엔티티의 img 필드에 이미지 경로 저장
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
            member.setImg("/images/" + fileName); // "/images/profile_12345.jpg"
            memberRepository.save(member);

            return ResponseEntity.ok("프로필 이미지가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
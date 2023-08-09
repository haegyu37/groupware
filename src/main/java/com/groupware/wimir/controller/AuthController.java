package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.*;
import com.groupware.wimir.entity.Authority;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
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

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final DocumentService documentService;

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
    public ResponseEntity<String> deleteMemberById(@PathVariable Long memberId) {
        try {
            Optional<Member> memberOptional = memberRepository.findById(memberId);
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
        Member member = memberRepository.findByNo(requestDto.getNo())
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        // 계정이 차단된 경우 토큰 발급하지 않고 접속 차단 응답 반환
        if (member.getAuthority() == Authority.ROLE_BLOCK) {
            log.info("접속이 차단된 계정입니다.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
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

    //해당 사원 정보보기
    @GetMapping("/admin/members/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
        return ResponseEntity.ok(memberResponseDTO);
    }

    @PostMapping("/admin/members/{memberId}/edit")
    public ResponseEntity<MemberResponseDTO> changeUserDetails(
            @PathVariable Long memberId,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestPart(name = "changeRequest", required = false) ChangeUserDTO changeRequest
    ) {
        try {
            // 사진 등록
            boolean imageUploaded = false;
            if (image != null) {
                String uploadDir = "src/main/resources/static/images";
                String fileName = "profile_" + memberId + "." + FilenameUtils.getExtension(image.getOriginalFilename());
                File file = new File(uploadDir + "/" + fileName);
                FileUtils.writeByteArrayToFile(file, image.getBytes());

                Member member = memberRepository.findById(memberId)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
                member.setImg("/images/" + fileName);
                memberRepository.save(member);

                log.info("사진 등록이 완료되었습니다. 사용자 ID: {}", memberId);
                imageUploaded = true;
            }

            // 비밀번호 변경
            boolean passwordChanged = false;
            if (changeRequest != null && changeRequest.getNewPassword() != null) {
                String updatedPassword = changeRequest.getNewPassword();
                MemberResponseDTO updatedUser = memberService.changeUserPasswordByAdmin(memberId, updatedPassword);
                log.info("비밀번호 변경이 완료되었습니다. 사용자 ID: {}", memberId);
                passwordChanged = true;
            }

            // 직급명 변경
            boolean positionChanged = false;
            if (changeRequest != null && changeRequest.getPosition() != null) {
                Position newPosition = changeRequest.getPosition();
                MemberResponseDTO updatedUser = memberService.changeUserPositionByAdmin(memberId, newPosition);
                log.info("직급명 변경이 완료되었습니다. 사용자 ID: {}", memberId);
                positionChanged = true;
            }

            // 팀명 변경
            boolean teamChanged = false;
            if (changeRequest != null && changeRequest.getTeam() != null) {
                Team newTeam = changeRequest.getTeam();
                MemberResponseDTO updatedUser = memberService.changeUserTeamByAdmin(memberId, newTeam);
                log.info("팀명 변경이 완료되었습니다. 사용자 ID: {}", memberId);
                teamChanged = true;
            }

            // 변경된 사용자 정보 조회
            Member updatedMember = memberRepository.findById(memberId)
                    .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

            MemberResponseDTO responseDTO = MemberResponseDTO.builder()
                    .id(updatedMember.getId())
                    .no(updatedMember.getNo())
                    .name(updatedMember.getName())
                    .position(updatedMember.getPosition())
                    .team(updatedMember.getTeam())
                    .authority(updatedMember.getAuthority())
                    .img(updatedMember.getImg())
                    .build();

            return ResponseEntity.ok(responseDTO);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }




    // 사용자의 권한을 ROLE_BLOCK으로 업데이트
    @PostMapping("/admin/members/{memberId}/block")
    public ResponseEntity<String> blockUser(@PathVariable Long memberId) {
        try {
            Optional<Member> memberOptional = memberRepository.findById(memberId);
            if (memberOptional.isPresent()) {
                memberService.updateUserAuthorityToBlock(memberId);
                return ResponseEntity.ok("사용자의 권한이 ROLE_BLOCK으로 업데이트되었습니다.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //결재완료된 모든 문서 목록
    @GetMapping("/listdone")
    public List<Document> approvedDocs() {
        List<Document> approvedDocs = documentService.getApprovedDocuments();
        return approvedDocs;
    }


}
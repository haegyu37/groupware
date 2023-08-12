package com.groupware.wimir.controller;


import com.groupware.wimir.DTO.ChangeUserDTO;
import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AuthService authService;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final DocumentService documentService;

    //직원 등록
    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto) {

        return ResponseEntity.ok(authService.signup(requestDto));
    }

    //직원 목록
    @GetMapping("/members")
    public ResponseEntity<List<MemberResponseDTO>> getAllMembers() {
        List<MemberResponseDTO> members = memberService.getAllMembers();
        return ResponseEntity.ok(members);
    }

    //직원 삭제
    @DeleteMapping("/members/{memberId}")
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

    //해당 사원 정보보기
    @GetMapping("/members/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Long id) {
        Member member = memberService.getMemberById(id);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);
        return ResponseEntity.ok(memberResponseDTO);
    }

    @PostMapping("/members/{memberId}/edit")
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
    @PostMapping("/members/{memberId}/block")
    @Transactional
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


    
}
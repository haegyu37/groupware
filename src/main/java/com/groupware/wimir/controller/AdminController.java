package com.groupware.wimir.controller;


import com.groupware.wimir.DTO.*;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.TemplateRepository;
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
    private final TemplateRepository templateRepository;

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
    @DeleteMapping("/delete/{memberId}")
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

    @PostMapping("/members/edit/{id}")
    public ResponseEntity<MemberResponseDTO> changeUserDetails(
            @PathVariable Long id,
            @RequestPart(name = "image", required = false) MultipartFile image,
            @RequestPart(name = "changeRequest", required = false) ChangeUserDTO changeUserDTO
    ) {
        try {
            // 사진 등록
            boolean imageUploaded = false;
            if (image != null) {
                String uploadDir = "src/main/resources/static/images";
                String fileName = "profile_" + id + "." + FilenameUtils.getExtension(image.getOriginalFilename());
                File file = new File(uploadDir + "/" + fileName);
                FileUtils.writeByteArrayToFile(file, image.getBytes());

                Member member = memberRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
                member.setImg("/images/" + fileName);
                memberRepository.save(member);

                log.info("사진 등록이 완료되었습니다. 사용자 ID: {}", id);
                imageUploaded = true;
            }

            // 비밀번호 변경
//            boolean passwordChanged = false;
            if (changeUserDTO.getNewPassword() != null) {
                String updatedPassword = changeUserDTO.getNewPassword();
                MemberResponseDTO memberResponseDTO = memberService.changeUserPasswordByAdmin(id, updatedPassword);
                log.info("비밀번호 변경이 완료되었습니다. 사용자 ID: {}", id);
//                passwordChanged = true;
            }

            // 직급명 변경
//            boolean positionChanged = false;
            if (changeUserDTO != null && changeUserDTO.getPosition() != null) {
                Position newPosition = changeUserDTO.getPosition();
                MemberResponseDTO updatedUser = memberService.changeUserPositionByAdmin(id, newPosition);
                log.info("직급명 변경이 완료되었습니다. 사용자 ID: {}", id);
//                positionChanged = true;
            }

            // 팀명 변경
//            boolean teamChanged = false;
            if (changeUserDTO != null && changeUserDTO.getTeam() != null) {
                Team newTeam = changeUserDTO.getTeam();
                MemberResponseDTO updatedUser = memberService.changeUserTeamByAdmin(id, newTeam);
                log.info("팀명 변경이 완료되었습니다. 사용자 ID: {}", id);
//                teamChanged = true;
            }

            // 변경된 사용자 정보 조회
            Member updatedMember = memberRepository.findById(id)
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

    //접속차단 앤나 접속차단 해제
    @PostMapping("/members/block")
    @Transactional
    public ResponseEntity<String> blockUser(@RequestBody MemberBlockDTO memberBlockDTO) {
        try {
            if (memberBlockDTO.getAuthority().equals(Authority.BLOCK)) {
                memberService.updateUserAuthorityToBlock(memberBlockDTO.getId());
                return ResponseEntity.ok("사용자의 권한이 BLOCK으로 업데이트되었습니다.");
            } else {
                memberService.updateBlockAuthorityToUser(memberBlockDTO.getId());
                return ResponseEntity.ok("사용자의 권한이 USER로 업데이트되었습니다.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 템플릿 생성 -> 관리자
    @PostMapping(value = "/templates/create")
    public Template createTemplate(@RequestBody TemplateDTO templateDTO) {
        Template template = new Template();
        template.setCategory(templateDTO.getCategory());
        template.setContent(templateDTO.getContent());
        return templateRepository.save(template);
    }

    // 템플릿 수정 -> 관리자
    @PutMapping(value = "/templates/update/{id}")
    public Template updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        Template updateTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));
        updateTemplate.setCategory(templateDTO.getCategory());
        updateTemplate.setCategory(templateDTO.getContent());

        return templateRepository.save(updateTemplate);
    }

    // 템플릿 삭제 -> 관리자
    @DeleteMapping(value = "/templates/delete/{id}")
    public void deleteTemplate(@PathVariable Long id) {
        templateRepository.deleteById(id);
    }

    //결재완료된 모든 문서 목록
    @GetMapping("/listdone")
    public List<Document> approvedDocs() {
        List<Document> approvedDocs = documentService.getApprovedDocuments();
        return approvedDocs;
    }




}
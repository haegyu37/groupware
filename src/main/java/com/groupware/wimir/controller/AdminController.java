package com.groupware.wimir.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
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
//    @PostMapping("/signup")
//    public ResponseEntity<?> signup( @RequestPart(value="post", required = true) MemberRequestDTO requestDto,  @RequestPart(value="image", required = true) MultipartFile multipartFile) {
//        try {
//            authService.signup(requestDto, multipartFile);
//            return ResponseEntity.ok(authService.signup(requestDto, multipartFile));
//
//        } catch (Exception e) {
//            // 예외 처리: 예외가 발생하면 에러 응답 반환
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 오류 발생: " + e.getMessage());
//        }
//    }
    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public ResponseEntity<?> signup(@RequestParam("post") String post, @RequestParam("image") MultipartFile multipartFile) {
        try {
            // 파일 업로드와 JSON 데이터 처리 로직
            // post 변수에는 JSON 데이터가 들어옵니다.
            MemberRequestDTO requestDto = new ObjectMapper().readValue(post, MemberRequestDTO.class);

            MemberResponseDTO responseDto = authService.signup(requestDto, multipartFile);
            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            // 예외 처리: 예외가 발생하면 에러 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 오류 발생: " + e.getMessage());
        }
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

    //직원 정보 수정
    @PostMapping("/members/edit/{id}")
    public Member editMember(@PathVariable Long id, @RequestBody ChangeUserDTO changeUserDTO) {
        Member member = memberRepository.findById(id).orElse(null);
        String newPassword = changeUserDTO.getNewPassword();
        String newImg = changeUserDTO.getImg();
        Team newTeam = changeUserDTO.getTeam();
        Position newPosition = changeUserDTO.getPosition();
        String newName = changeUserDTO.getName();

        if (newPassword == null && newImg == null && newTeam == null && newPosition == null && newName == null) {
            throw new IllegalArgumentException("수정값 없음");
        }

        // 비밀번호 변경
        if (newPassword != null) {
            memberService.changeUserPasswordByAdmin(id, newPassword);
        }

//        // 사진 변경
//        if (newImg != null) {
//            member.setImg(newImg);
//        }

        // 팀 변경
        if (newTeam != null) {
            member.setTeam(newTeam);
        }

        // 직급 변경
        if (newPosition != null) {
            member.setPosition(newPosition);
        }

        // 이름 변경
        if (newName != null) {
            member.setName(newName);
        }

        memberRepository.save(member);
        Member newMember = memberRepository.findById(id).orElse(null);
        return newMember;
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
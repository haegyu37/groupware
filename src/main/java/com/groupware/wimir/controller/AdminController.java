package com.groupware.wimir.controller;


import com.fasterxml.classmate.MemberResolver;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupware.wimir.DTO.*;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.ProfileRepository;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.AuthService;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.MemberService;
import com.groupware.wimir.service.ProfileService;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.*;
import java.util.stream.Collectors;

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
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;
    @Value("${ProfileLocation}")
    private String profileLocation;

    //직원 등록
    @PostMapping(value = "/signup", consumes = "multipart/form-data")
    public ResponseEntity<?> signup(@RequestPart("post") String post, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws Exception {
        try {
            MemberRequestDTO requestDto = new ObjectMapper().readValue(post, MemberRequestDTO.class);

            MemberResponseDTO responseDto = authService.signup(requestDto, multipartFile);

            return ResponseEntity.ok(responseDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 가입 중 오류 발생: " + e.getMessage());
        }
    }


    //직원 목록
    @GetMapping("/members")
    public List<MemberResponseDTO> getAllMembers() {
        List<Member> memberList = memberRepository.findAll();
        List<MemberResponseDTO> memberResponseDTOList = new ArrayList<>(); // 결과를 담을 리스트

        for (Member member : memberList) {
            Profile profile = profileService.getMaxProfile(member);
            MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);

            if (profile != null) {
                memberResponseDTO.setImage(profile.getImgName());
            }

            memberResponseDTOList.add(memberResponseDTO); // 리스트에 추가
        }
        return memberResponseDTOList;
    }

    //직원 삭제
    @PostMapping("/delete/{id}")
    public Member deleteUser(@PathVariable Long id) {
        return memberService.deleteMember(id);
    }


    //직원 조회
    @GetMapping("/members/{id}")
    public MemberResponseDTO getMemberById(@PathVariable Long id) throws IOException {
        Member member = memberService.findMemberById(id);
        Profile profile = profileService.getMaxProfile(member);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);

        if (profile != null) {
            memberResponseDTO.setImage(profile.getImgName());
        }

        return memberResponseDTO;
    }

    //직원 정보 수정
    @PostMapping(value = "/members/edit/{id}", consumes = "multipart/form-data")
    public Member editMember(@PathVariable Long id, @RequestPart("post") String post, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws Exception {
        Member member = memberRepository.findById(id).orElse(null);
        ChangeUserDTO changeUserDTO = new ObjectMapper().readValue(post, ChangeUserDTO.class);
        String newPassword = changeUserDTO.getNewPassword();
        Team newTeam = changeUserDTO.getTeam();
        Position newPosition = changeUserDTO.getPosition();
        String newName = changeUserDTO.getName();

        if (newPassword == null && multipartFile == null && newTeam == null && newPosition == null && newName == null) {
            throw new IllegalArgumentException("수정값 없음");
        }

        // 비밀번호 변경
        if (newPassword != null) {
            memberService.changeUserPasswordByAdmin(id, newPassword);
        }

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

        // 프로필 업데이트
        Profile profile = new Profile();
        profile.setMember(member);
        if (multipartFile != null) {
            profileService.saveProfile(profile, multipartFile);
        }


        memberRepository.save(member);
        Member newMember = memberRepository.findById(id).orElse(null);
        return newMember;
    }


    //접속차단 앤나 접속차단 해제
    @PostMapping("/members/block")
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
    @PutMapping(value = "/templates/edit/{id}")
    public Template updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        Template updateTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));
        updateTemplate.setCategory(templateDTO.getCategory());
        updateTemplate.setContent(templateDTO.getContent());

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
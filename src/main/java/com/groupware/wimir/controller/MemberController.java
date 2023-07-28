package com.groupware.wimir.controller;


import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private Member member;
    @Autowired
    private MemberRepository memberRepository;

    //마이페이지
    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> getMyMemberInfo() {
        MemberResponseDTO myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getName());
        return ResponseEntity.ok((myInfoBySecurity));
    }

    //비밀번호 변경
    @PostMapping("/password")
    public ResponseEntity<MemberResponseDTO> setMemberPassword(@RequestBody com.groupware.wimir.DTO.ChangePasswordRequestDTO request) {
        System.out.println("비밀번호 변경완료");
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getNewPassword()));

    }

    // 사진등록
    @PostMapping("/profile-image")
    public ResponseEntity<String> uploadProfileImage(@RequestParam("image") MultipartFile image) {
        try {
            // 이미지를 저장할 디렉토리 경로 지정
            String uploadDir = "src/main/resources/static/images"; //

            // 이미지 파일 이름 생성 (예시: "profile_12345.jpg")
            String fileName = "profile_" + SecurityUtil.getCurrentMemberId() + "." + FilenameUtils.getExtension(image.getOriginalFilename());

            // 이미지를 지정된 경로에 저장
            File file = new File(uploadDir + "/" + fileName);
            FileUtils.writeByteArrayToFile(file, image.getBytes());

            // Member 엔티티의 img 필드에 이미지 경로 저장
            Long currentMemberId = SecurityUtil.getCurrentMemberId();
            Member member = memberRepository.findById(currentMemberId)
                    .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
            member.setImg("/images/" + fileName); // 예시: "/images/profile_12345.jpg"
            memberRepository.save(member);

            return ResponseEntity.ok("프로필 이미지가 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}



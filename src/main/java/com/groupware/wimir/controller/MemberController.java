package com.groupware.wimir.controller;


import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ChangePasswordRequestDTO;
import com.groupware.wimir.DTO.ChangeUserDTO;
import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.exception.ResourceNotFoundException;
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
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {

    @Autowired
    private MemberService memberService;
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
//    @PostMapping("/profile")
//    public ResponseEntity<String> updateProfile(
//            @RequestPart(name = "image", required = false) MultipartFile image,
//            @RequestPart(name = "changePassword", required = false) ChangePasswordRequestDTO passwordRequest
//    ) {
//        try {
//            // 현재 로그인한 사용자의 ID 가져오기
//            Long currentMemberId = SecurityUtil.getCurrentMemberId();
//
//            boolean imageUploaded = false;
//            boolean passwordChanged = false;
//
//            // 프로필 이미지 업로드 요청인지 확인
//            if (image != null) {
//                String uploadDir = "src/main/resources/static/images";
//                String fileName = "profile_" + currentMemberId + "." + FilenameUtils.getExtension(image.getOriginalFilename());
//                File file = new File(uploadDir + "/" + fileName);
//                FileUtils.writeByteArrayToFile(file, image.getBytes());
//
//                Member member = memberRepository.findById(currentMemberId)
//                        .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다."));
//                member.setImg("/images/" + fileName);
//                memberRepository.save(member);
//
//                log.info("프로필 이미지가 성공적으로 등록되었습니다. 사용자 ID: {}", currentMemberId);
//                imageUploaded = true;
//            }
//
//            // 비밀번호 변경 요청인지 확인
//            if (passwordRequest != null && passwordRequest.getNewPassword() != null) {
//                MemberResponseDTO updatedUser = memberService.changeMemberPassword(passwordRequest.getNewPassword());
//                log.info("비밀번호 변경이 완료되었습니다. 사용자 ID: {}", currentMemberId);
//                passwordChanged = true;
//            }
//
//            // 요청에 변경 사항이 없는 경우
//            if (!imageUploaded && !passwordChanged) {
//                return ResponseEntity.ok("요청에 변경 사항이 없습니다.");
//            }
//
//            if (imageUploaded && passwordChanged) {
//                return ResponseEntity.ok("프로필 이미지와 비밀번호 변경이 모두 성공적으로 처리되었습니다.");
//            } else if (imageUploaded) {
//                return ResponseEntity.ok("프로필 이미지가 성공적으로 등록되었습니다.");
//            } else {
//                return ResponseEntity.ok("비밀번호 변경이 완료되었습니다.");
//            }
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }

    //내 정보 수정 : 사진 수정, 비밀번호 수정
//    @PostMapping("/edit")
//    public Member editMyInfo(MemberResponseDTO memberResponseDTO) {
//        Long currentId = SecurityUtil.getCurrentMemberId();
//        Member member = memberRepository.findById(currentId).orElseThrow(() -> new ResourceNotFoundException("id를 찾을 수 없습니다: " + currentId));
//        if (memberResponseDTO.getImg() != null) {
//            member.setImg(memberResponseDTO.getImg());
//        }
//        if (memberResponseDTO.getPassword() != null) {
//            member.setPassword(memberResponseDTO.getPassword());
//        }
//        Member updateMember = memberRepository.save(member);
//        return updateMember;
//    }


    //내 정보 수정 -> 비밀번호 변경
    @PostMapping("/edit")
    public Member editMyInfo(@RequestBody ChangeUserDTO changeUserDTO) {
        Long currentId = SecurityUtil.getCurrentMemberId();
        String newPassword = changeUserDTO.getNewPassword();

        if (newPassword == null || newPassword.isEmpty()) {
         throw new IllegalArgumentException("새 비밀번호를 입력해주세요");
        }

        memberService.changeUserPasswordByAdmin(currentId, newPassword);
        Member newMember = memberRepository.findById(currentId).orElse(null);
        return newMember;
    }

}



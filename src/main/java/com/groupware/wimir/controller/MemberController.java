package com.groupware.wimir.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ChangePasswordRequestDTO;
import com.groupware.wimir.DTO.ChangeUserDTO;
import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Profile;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.MemberService;
import com.groupware.wimir.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
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
    @Autowired
    private ProfileService profileService;

    //마이페이지
    @GetMapping("/me")
    public Map<String, Object> getMyMemberInfo() {
        Long currentId = SecurityUtil.getCurrentMemberId();
        Member member = memberService.findMemberById(currentId);
//        MemberResponseDTO myInfoBySecurity = memberService.getMyInfoBySecurity();

        Profile profile = profileService.getMaxProfile(member);
        Path imagePath = null;

        if (profile != null) {
            imagePath = Paths.get(profile.getImgUrl());
        } else {
            imagePath = Paths.get("");
        }

        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);

        // Map에 데이터 추가
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("memberResponseDTO", memberResponseDTO);
        responseMap.put("imagePath", imagePath.toString());

        return responseMap;
    }

//    //마이페이지
//    @GetMapping("/me")
//    public ResponseEntity<MemberResponseDTO> getMyMemberInfo() {
//        MemberResponseDTO myInfoBySecurity = memberService.getMyInfoBySecurity();
//        System.out.println(myInfoBySecurity.getName());
//        return ResponseEntity.ok((myInfoBySecurity));
//    }


    //내 정보 수정 -> 비밀번호 변경, 이미지 수정
    @PostMapping("/edit")
    public Member editMyInfo(@RequestPart("post") String post, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws Exception {
        ChangeUserDTO changeUserDTO = new ObjectMapper().readValue(post, ChangeUserDTO.class);

        Long currentId = SecurityUtil.getCurrentMemberId();
        String newPassword = changeUserDTO.getNewPassword();

        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("새 비밀번호를 입력해주세요");
        }

        memberService.changeUserPasswordByAdmin(currentId, newPassword);
        Member newMember = memberRepository.findById(currentId).orElse(null);

        // 프로필 업데이트
        Profile profile = new Profile();
        profile.setMember(newMember);
        if (multipartFile != null) {
            profileService.saveProfile(profile, multipartFile);
        }

        return newMember;
    }

}



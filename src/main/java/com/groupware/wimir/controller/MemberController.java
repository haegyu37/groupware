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



package com.groupware.wimir.controller;


import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ChangePasswordRequestDTO;
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

    //사진등록
    @PostMapping("/edit")
    public Member editMyInfo(MemberResponseDTO memberResponseDTO){
        Long currentId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(currentId).orElseThrow(() -> new ResourceNotFoundException("id를 찾을 수 없습니다: " + currentId));
        member.setImg(memberResponseDTO.getImg());
        memberRepository.save(member);
        return member;
    }
}



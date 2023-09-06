package com.groupware.wimir.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.*;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
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
    @Value("${ProfileLocation}")
    private String profileLocation;

    //마이페이지
    @GetMapping("/me")
    public MemberResponseDTO getMyMemberInfo() throws IOException {
        Long currentId = SecurityUtil.getCurrentMemberId();
        Member member = memberService.findMemberById(currentId);
        Profile profile = profileService.getMaxProfile(member);
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.of(member);

        if (profile != null) {
            memberResponseDTO.setImage(profile.getImgName());
        }
        System.out.println("내사진" + memberResponseDTO.getImage());
        return memberResponseDTO;
    }

    @CrossOrigin
    @GetMapping("/image")
    public ResponseEntity<?> returnImage(@RequestParam String imageName) {
        String path = profileLocation;
        Resource resource = new FileSystemResource(path + imageName);
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    //내 정보 수정 -> 비밀번호 변경, 이미지 수정
    @PostMapping("/edit")
    public Member editMyInfo(@RequestPart("post") String post, @RequestPart(value = "image", required = false) MultipartFile multipartFile) throws Exception {
        ChangeUserDTO changeUserDTO = new ObjectMapper().readValue(post, ChangeUserDTO.class);

        Long currentId = SecurityUtil.getCurrentMemberId();
        String newPassword = changeUserDTO.getNewPassword();

        if (newPassword != null) {
            memberService.changeUserPasswordByAdmin(currentId, newPassword);
        }

        Member newMember = memberRepository.findById(currentId).orElse(null);
        Profile profile = new Profile();
        profile.setMember(newMember);
        if (multipartFile != null) {
            profileService.saveProfile(profile, multipartFile);
        }
        return newMember;
    }

    @GetMapping("/search")
    public List<Member> searchMembers(@RequestBody MemberSerchDTO memberSerchDTO) {
        List<Member> members = memberRepository.getMembers(memberSerchDTO);
        return members;
    }

}



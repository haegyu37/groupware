package com.groupware.wimir.controller;


import com.groupware.wimir.dto.ChangePasswordRequestDTO;
import com.groupware.wimir.dto.MemberResponseDTO;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<MemberResponseDTO> getMyMemberInfo() {
        MemberResponseDTO myInfoBySecurity = memberService.getMyInfoBySecurity();
        System.out.println(myInfoBySecurity.getName());
        return ResponseEntity.ok((myInfoBySecurity));
        // return ResponseEntity.ok(memberService.getMyInfoBySecurity());
    }


    @PostMapping("/password")
    public ResponseEntity<MemberResponseDTO> setMemberPassword(@RequestBody com.groupware.wimir.dto.ChangePasswordRequestDTO request) {
        System.out.println("비밀번호 변경완료");
        return ResponseEntity.ok(memberService.changeMemberPassword(request.getNewPassword()));

    }

}
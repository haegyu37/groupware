package com.groupware.wimir.controller;


import com.groupware.wimir.dto.ChangePasswordRequestDTO;
import com.groupware.wimir.dto.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    //팀 모두 출력
    @GetMapping("/teams")
    public List<String> getAllTeams() {
        List<String> teamNames = new ArrayList<>();
        Team[] teams = Team.values();
        for (Team team : teams) {
            teamNames.add(team.name());
        }
        return teamNames;
    }

    @GetMapping("/{team}")
    public List<Member> getTeamMembers(@PathVariable("team") Team team) {
        return memberRepository.findByTeam(team);
    }

}
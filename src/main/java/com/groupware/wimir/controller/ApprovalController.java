package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.LineDTO;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.LineRepository;
import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.LineService;
import com.groupware.wimir.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/approval")
@SessionAttributes("loginMember")
public class ApprovalController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ApprovalService approvalService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private LineService lineService;

    //팀 모두 출력
    @GetMapping("/team")
    public List<String> getAllTeam() {
        List<String> teamNames = new ArrayList<>();
        Team[] teams = Team.values();
        for (Team team : teams) {
            teamNames.add(team.name());
        }
        return teamNames;
    }

    //직급 모두 출력
    @GetMapping("/position")
    public List<String> getAllPosition() {
        List<String> positionNames = new ArrayList<>();
        Position[] positions = Position.values();
        for (Position position : positions) {
            positionNames.add(position.name());
        }
        return positionNames;
    }

    //팀원 출력
    @GetMapping("/team/{team}")
    public List<Member> getTeamMembers(@PathVariable("team") Team team) {

        return memberRepository.findByTeam(team);
    }

    //직급원 출력
    @GetMapping("/position/{position}")
    public List<Member> getPositionMembers(@PathVariable("position") Position position) {

        return memberRepository.findByPosition(position);
    }

    //조직도 출력
    @GetMapping("/members")
    public List<String> getAllMembersByTeam() {
        List<Member> members = memberService.getAllMembersByTeam();

        List<String> outputList = new ArrayList<>();
        String currentTeam = null;

        for (Member member : members) {
            String teamName = member.getTeam() != null ? member.getTeam().name() : "null";
            if (currentTeam == null || !currentTeam.equals(teamName)) {
                outputList.add(teamName);
                currentTeam = teamName;
            }

            String positionName = member.getPosition() != null ? member.getPosition().name() : "";
            outputList.add(member.getName() + " " + positionName);
        }

        return outputList;
    }



    }






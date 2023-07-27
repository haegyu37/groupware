package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/approval")
@SessionAttributes("loginMember")
public class ApprovalController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    ApprovalRepository approvalRepository;

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

    @PostMapping("/request")
    public ResponseEntity<Approval> setApprovalRequest(@RequestBody ApprovalDTO approvalDTO) {

        for (Long approverId : approvalDTO.getApprovers()) {
            Approval line = new Approval();

            line.setMemberId(approverId);
            line.setName(approvalDTO.getName());
            line.setWriter(SecurityUtil.getCurrentMemberId());

            return ResponseEntity.ok(approvalRepository.save(line));
        }

        // 반드시 ResponseEntity를 반환해야 하므로 해당 부분에 대한 처리를 추가할 수 있습니다.
        // 예를 들어, 아래와 같이 처리하면 됩니다.
        return ResponseEntity.badRequest().build();
    }
}





package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/approval")
public class ApprovalController {

    @Autowired
    private MemberRepository memberRepository;

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

}

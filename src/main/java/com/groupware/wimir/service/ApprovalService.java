package com.groupware.wimir.service;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class ApprovalService {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    public List<Member> createApprovalLine(List<Long> approverIds) {
        List<Member> approvalLine = new ArrayList<>();

        // 개인 별로 결재자 지정
        for (Long memberId : approverIds) {
            Member approver = memberService.getMemberById(memberId);
            approvalLine.add(approver);
        }

        return approvalLine;
    }

    public List<Member> createTeamApprovalLine(String team) {
        List<Member> teamMembers = memberService.getMembersByTeam(Team.valueOf(team));

        // 부서 전체를 선택하는 경우 직급 순서대로 결재자 정렬
        Collections.sort(teamMembers, Comparator.comparing(Member::getPosition));

        return teamMembers;
    }
}

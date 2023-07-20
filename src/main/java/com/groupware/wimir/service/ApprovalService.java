package com.groupware.wimir.service;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.ApprovalRepository;
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

    @Autowired
    ApprovalRepository approvalRepository;


    public void createApprovalLine(ApprovalDTO approvalDTO) {
        List<Member> memberList = new ArrayList<>();
        for(int i=0; i<approvalDTO.getMemberList().size(); i++){
            memberList.add(memberService.getMemberById(approvalDTO.getMemberList().get(i)));
        }
        Document document = approvalDTO.getDocument();
        // 개인 별로 결재자 지정
        for (Member member : memberList) {

            Approval approval = new Approval();
            approval.setApprover(member);
            approval.setDocument(document);

            approvalRepository.save(approval);
        }

    }

    public List<Member> createTeamApprovalLine(String team) {
        List<Member> teamMembers = memberService.getMembersByTeam(Team.valueOf(team));

        // 부서 전체를 선택하는 경우 직급 순서대로 결재자 정렬
        Collections.sort(teamMembers, Comparator.comparing(Member::getPosition));

        return teamMembers;
    }
}

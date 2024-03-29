package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.jena.base.Sys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    ApprovalService approvalService;
    @Autowired
    ApprovalRepository approvalRepository;
    @Autowired
    DocumentRepository documentRepository;

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
        List<Member> members = memberRepository.findByTeam(team);


        //block 계정 출력 안됨
        List<Member> filteredMembers = members.stream()
                .filter(member -> !member.getAuthority().equals(Authority.BLOCK) && !member.getAuthority().equals(Authority.DELETE))
                .collect(Collectors.toList());

        return filteredMembers;
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

    //내가 결재라인인 문서 목록
    @GetMapping("/list")
    public List<Document> getMyApprovals() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //id를 기준으로 Approval을 찾는 메소드
        List<Document> myAppDocs = approvalService.getApprovals(currentMemberId);
        return myAppDocs;
    }

    //내가 결재라인인 문서 목록 근데 이제 내 차례인 ..
    @GetMapping("/listnow")
    public List<Document> getMyApprovalsNow() {
        Long currentId = SecurityUtil.getCurrentMemberId();
        //id를 기준으로 Approval을 찾는 메소드
        List<Document> myAppDocs = approvalService.getApprovalsNow(currentId);
        System.out.println("나" + currentId);
        return myAppDocs;
    }

    //내가 참조인 문서 리스트
    @GetMapping("/listrefer")
    public List<Document> referDocs() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return approvalService.getReferencedDocuments(currentMemberId);
    }

    //내가 승인 앤나 반려한 리스트 just 내가 승인/반려 한 문서 리스트
    @GetMapping("/list/approve")
    public List<Document> getMyApproved() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //id를 기준으로 Approval을 찾는 메소드
        List<Document> myAppDocs = approvalService.getApproved(currentMemberId);
        return myAppDocs;
    }

    @GetMapping("/list/reject")
    public List<Document> getMyRejected() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //id를 기준으로 Approval을 찾는 메소드
        List<Document> myAppDocs = approvalService.getRejected(currentMemberId);
        return myAppDocs;
    }


    //결재 승인 앤나 반려
    @PostMapping("/approve")
    public ResponseEntity<String> approveDocument(@RequestBody ApprovalDTO approvalDTO) {
        Long currentId = SecurityUtil.getCurrentMemberId();
        if (approvalDTO.getStatus().equals("승인")) {
            approvalService.approveDocument(approvalDTO.getDocument(), currentId);
            return ResponseEntity.ok("결재가 승인되었습니다.");
        } else if (approvalDTO.getStatus().equals("반려")) {
            approvalService.rejectDocument(approvalDTO, approvalDTO.getDocument());
            return ResponseEntity.ok("결재가 반려되었습니다.");
        } else {
            return ResponseEntity.ok("결재 중 오류가 발생했습니다.");
        }
    }

    //결재 취소
    @PostMapping("/cancel")
    public ResponseEntity<?> cancleApproval(@RequestBody ApprovalDTO approvalDTO) {
        Long currentId = SecurityUtil.getCurrentMemberId();
        ResponseEntity<?> result = approvalService.cancelApproval(approvalDTO.getDocument(), currentId);
        if (result == null) {
            return ResponseEntity.ok("다음 결재자가 취소 후 결재 취소가 가능합니다.");
        } else {
            return ResponseEntity.ok("결재가 취소되었습니다.");
        }
    }

    //결재 회수
    @PostMapping("/back")
    public ResponseEntity<String> backApproval(@RequestBody ApprovalDTO approvalDTO) {
        Document document = documentRepository.findById(approvalDTO.getDocument()).orElse(null);
//        Long id = document.getId();
        List<Approval> approvals = approvalRepository.findByDocument(document);
        Approval secondApprover = approvals.get(1);

        //두번째 결재자가 이미 결재 했으면 결재 취소 먼저 요청해야됨
        if (secondApprover.getStatus() != null || secondApprover.getAppDate() != null) {
            return ResponseEntity.ok("이미 결재가 진행된 건을 회수할 수 없습니다.");
        }
        approvalService.backApproval(approvalDTO.getDocument());
        return ResponseEntity.ok("결재가 회수되었습니다.");

    }
}






package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ApprovalDTO;
//import com.groupware.wimir.DTO.ApprovalRequestDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.DTO.LineDTO;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.DocumentService;
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
    private LineService lineService;
    @Autowired
    ApprovalRepository approvalRepository;
    @Autowired
    DocumentService documentService;

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

    //결재 요청
//    @PostMapping()
//    public ResponseEntity<Approval> setApprovalRequest(@RequestBody ApprovalDTO approvalDTO, @RequestBody LineDTO lineDTO){
//        Approval approval = new Approval();
////        Document document = new Document();
//
//        approval.setApproved(0); //결재 완료 전
//        approval.setApprovalDate(null); //결재 완료 전
//        approval.setDocument(approvalDTO.getDocumentId()); //문서번호 저장
//
//        approvalRepository.save(approval);
//
//        List<Long> approvers = lineDTO.getApprovers(); //List<Long>으로 입력받은 값을 approvers에 대입
//
//        for (Long approverId : approvers) { //List approvers 각각의 값을 approverId에 대입
//            if (approverId != null) {
//                ApprovalLine line = new ApprovalLine();
//                String name = lineDTO.getName();
//
//                line.setMemberId(approverId);
//                line.setName(name);
//                line.setWriter(SecurityUtil.getCurrentMemberId());
//
//                lineRepository.save(line); //MemberId, Name, Writer값 각각 저장
//            }
//        }
//
//        return ResponseEntity.ok(approval);
//
//    }

//    @PostMapping("/request")
//    public ResponseEntity<Approval> setApprovalRequest(@RequestBody ApprovalDTO approvalDTO) {
//
//        List<Long> approvers = approvalDTO.getApprovers(); // List<Long>으로 입력받은 값을 approvers에 대입
//
//        for (Long approverId : approvers) { // List approvers 각각의 값을 approverId에 대입
//            Approval line = new Approval();
//
//            line.setMemberId(approverId);
//            line.setName(approvalDTO.getName());
//            line.setWriter(SecurityUtil.getCurrentMemberId());
//
//            return ResponseEntity.ok(approvalRepository.save(line)); // MemberId, Name, Writer값 각각 저장
//        }
//    }




    }






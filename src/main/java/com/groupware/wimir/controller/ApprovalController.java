//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.Config.SecurityUtil;
//import com.groupware.wimir.DTO.ApprovalDTO;
//import com.groupware.wimir.entity.*;
//import com.groupware.wimir.repository.ApprovalRepository;
//import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
//import com.groupware.wimir.service.MemberService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.NoSuchElementException;
//import java.util.stream.Collectors;
//
//@Slf4j
//@RestController
//@RequestMapping("/approval")
//@SessionAttributes("loginMember")
//public class ApprovalController {
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private MemberService memberService;
////    @Autowired
////    ApprovalRepository approvalRepository;
//    @Autowired
//    ApprovalService approvalService;
//
//    //팀 모두 출력
//    @GetMapping("/team")
//    public List<String> getAllTeam() {
//        List<String> teamNames = new ArrayList<>();
//        Team[] teams = Team.values();
//        for (Team team : teams) {
//            teamNames.add(team.name());
//        }
//        return teamNames;
//    }
//
//    //직급 모두 출력
//    @GetMapping("/position")
//    public List<String> getAllPosition() {
//        List<String> positionNames = new ArrayList<>();
//        Position[] positions = Position.values();
//        for (Position position : positions) {
//            positionNames.add(position.name());
//        }
//        return positionNames;
//    }
//
//    //팀원 출력
//    @GetMapping("/team/{team}")
//    public List<Member> getTeamMembers(@PathVariable("team") Team team) {
//
//        return memberRepository.findByTeam(team);
//    }
//
//    //직급원 출력
//    @GetMapping("/position/{position}")
//    public List<Member> getPositionMembers(@PathVariable("position") Position position) {
//
//        return memberRepository.findByPosition(position);
//    }
//
//    //조직도 출력
//    @GetMapping("/members")
//    public List<String> getAllMembersByTeam() {
//        List<Member> members = memberService.getAllMembersByTeam();
//
//        List<String> outputList = new ArrayList<>();
//        String currentTeam = null;
//
//        for (Member member : members) {
//            String teamName = member.getTeam() != null ? member.getTeam().name() : "null";
//            if (currentTeam == null || !currentTeam.equals(teamName)) {
//                outputList.add(teamName);
//                currentTeam = teamName;
//            }
//
//            String positionName = member.getPosition() != null ? member.getPosition().name() : "";
//            outputList.add(member.getName() + " " + positionName);
//        }
//
//        return outputList;
//    }
//
//    //내가 결재라인인 문서 목록
//    @GetMapping("/mylist")
//    public List<Document> getMyApprovals() {
//        Long currentMemberId = SecurityUtil.getCurrentMemberId();
//        //id를 기준으로 Approval을 찾는 메소드
//        List<Document> myAppDocs = approvalService.getApprovals(currentMemberId);
//        return myAppDocs;
//    }
//
//    //결재 승인 앤나 반려
//    @PostMapping("/approve")
//    public ResponseEntity<String> approveDocument(@RequestBody ApprovalDTO approvalDTO) {
//            if (approvalDTO.getStatus() == 1) {
//                approvalService.approveDocument(approvalDTO.getDocument());
//                return ResponseEntity.ok("결재가 승인되었습니다.");
//            } else if(approvalDTO.getStatus() == 2){
//                approvalService.rejectDocument(approvalDTO, approvalDTO.getDocument());
//                return ResponseEntity.ok("결재가 반려되었습니다.");
//            } else {
//                return ResponseEntity.ok("결재 중 오류가 발생했습니다.");
//            }
//    }
//
//
//
//
//
//
//
//
//
//}
//
//
//
//
//

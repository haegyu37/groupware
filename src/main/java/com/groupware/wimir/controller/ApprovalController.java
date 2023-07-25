//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.DTO.ApprovalDTO;
//import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.entity.Position;
//import com.groupware.wimir.entity.Team;
//import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
//import com.groupware.wimir.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping("/approval")
//public class ApprovalController {
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private ApprovalService approvalService;
//
//    @Autowired
//    private ApprovalService service;
//
//    @Autowired
//    private MemberService service2;
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
//    //결재 메인화면: 결재할 문서, 결재 중 문서, 결재 완료한 문서 출력
//    @GetMapping
//    public ModelAndView approvalMain(@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
//        ModelAndView model = new ModelAndView("approval/approvalMain");
//
//        int approvalCount_YET = approvalService.approvalCount_YET(loginMember); //결재할 문서
//        int approvalCount_UNDER = approvalService.approvalCount_UNDER(loginMember); //결재 중 문서
//        int approvalCount_DONE = approvalService.approvalCount_DONE(loginMember); //결재 완료한 문서
//
//        List<Approval> mainList = approvalService.getRecentList(loginMember); //내 결재 목록
//        List<Approval> mainList1 = approvalService.getRecentList1(loginMember); //내가 작성한 결재
//        List<Approval> mainList2 = approvalService.getRecentList2(loginMember); //결재 수신목록
//
//        model.addObject("mainList", mainList);
//        model.addObject("mainList1", mainList1);
//        model.addObject("mainList2", mainList2);
//        model.addObject("countYet", approvalCount_YET);
//        model.addObject("countUnder", approvalCount_UNDER);
//        model.addObject("countDone", approvalCount_DONE);
//
//        return model;
//    }
//
//
//
//}

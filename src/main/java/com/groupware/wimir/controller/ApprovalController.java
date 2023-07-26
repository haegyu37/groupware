package com.groupware.wimir.controller;

import com.google.protobuf.Message;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.DocumentServiceImpl;
import com.groupware.wimir.service.MemberService;
import com.mysql.cj.xdevapi.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private DocumentServiceImpl documentService;
    @Autowired
    private ApprovalRepository approvalRepository;

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

    //결재라인 저장하기
    @PostMapping("/save")
    public List<Long> saveApprovalLine(@RequestBody ApprovalDTO approvalDTO) {

        Approval approval = new Approval();

        approval.setDocument(approvalDTO.getDocumentId());
        approval.setApproverIds(approvalDTO.getApproverIds());
        approval.setApprovalDate(approvalDTO.getApprovalDate());
        approval.setApproved(approvalDTO.getApproved());
        approval.setName(approvalDTO.getName());

        return approvalService.saveApprovalIds(approval);
    }

//    @PostMapping("/save")
//    public List<Long> saveApprovalLine(@RequestBody ApprovalDTO approvalDTO) {
//
//        // Convert the Document ID to a Document entity (assuming you have a method to fetch the Document entity)
//        Document document = documentService.getDocumentById(approvalDTO.getDocumentId());
//
//        // Create a list to hold the Approvers
//        List<ApprovalLine> approvers = new ArrayList<>();
//
//        // Loop through the list of approver IDs and create Approvers for each ID
//        for (Long approverId : approvalDTO.getApproverIds()) {
//            ApprovalLine line = new ApprovalLine();
//            line.setApproverId(approverId);
//            line.setApproval(ap proval); // Set the relationship with the Approval entity
//            approvers.add(line);
//        }
//
//        // Create the Approval entity
//        Approval approval = new Approval();
//        approval.setDocument(document);
//        approval.setApprovers(approvers); // Set the list of Approvers
//        approval.setApprovalDate(approvalDTO.getApprovalDate());
//        approval.setApproved(approvalDTO.getApproved());
//        approval.setName(approvalDTO.getName());
//
//        // Save the Approval entity, including the Approvers
//        approvalService.saveApproval(approval);
//
//        // Return the list of Approver IDs for response (if needed)
//        return approvalDTO.getApproverIds();
//    }

    //저장한 결재라인 모두 불러오기
    @GetMapping("/lineList")
    public ResponseEntity<List<Approval>> getAllApprovals() {
        List<Approval> approvals = approvalService.getAllApprovals();
        return new ResponseEntity<>(approvals, HttpStatus.OK);
    }





}

package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.LineDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/line")
@SessionAttributes("loginMember")
public class LineController {

    @Autowired
    private LineService lineService;
    @Autowired
    private ApprovalRepository approvalRepository;

    //마이페이지에서 결재라인 저장하기
    @PostMapping("/create")
    public ResponseEntity<String> saveApprovalLine(@RequestBody LineDTO lineDTO) {
        lineService.saveApprovalLine(lineDTO);
        return ResponseEntity.ok("결재라인을 저장했습니다.");
    }

    //결재라인 목록
    @GetMapping("/list")
    public Map<Long, List<Map<String, Object>>> getMemberLineById() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        List<Approval> allApprovals = approvalRepository.findByWriter(currentMemberId);
        return lineService.getGroupedApprovals(allApprovals);
    }

    //결재라인 조회
    @GetMapping("/{id}")
    public Map<String, List<Map<String, Object>>> getMemberInfoLineById(@PathVariable Long id) {
        List<Approval> approvals = lineService.getByLineId(id);
        return lineService.getGroupedApprovalsName(approvals);
    }

    //결재라인 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteDocument(@PathVariable Long id) {
        lineService.deleteDocumentByLineId(id);
    }


}

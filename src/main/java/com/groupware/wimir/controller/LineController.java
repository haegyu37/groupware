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
    @Autowired
    private MemberRepository memberRepository;

    //마이페이지에서 결재라인 저장하기
    @PostMapping("/create")
    public ResponseEntity<String> saveApprovalLine(@RequestBody LineDTO lineDTO) {

        Long maxLineId = approvalRepository.findMaxLineId();
        if (maxLineId == null) {
            maxLineId = 1L;
        } else {
            maxLineId = maxLineId + 1;
        }

        int lastIndex = lineDTO.getApprovers().size() - 1; // 배열의 맨 마지막 인덱스
        List<Long> curAppList = lineDTO.getApprovers();
        curAppList.add(0, SecurityUtil.getCurrentMemberId());

        for (int i = 0; i < curAppList.size(); i++) {
            Long approverId = curAppList.get(i);
            if (approverId != null) {
                Approval approval = new Approval();

                //첫번째 결재자는 기안자
                if (i == 0) {
                    approval.setMemberId(SecurityUtil.getCurrentMemberId());
                }

                approval.setMemberId(approverId);
                approval.setName(lineDTO.getName());
                approval.setWriter(SecurityUtil.getCurrentMemberId());
                approval.setCategory(lineDTO.getCategory());
                approval.setLineId(maxLineId);
                approval.setRefer("결재");

                // 맨 마지막 인덱스인 경우 refer를 "참조"로 설정
                if (i == lastIndex) {
                    approval.setRefer("참조");
                }

                approvalRepository.save(approval);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("직원을 찾을 수 없습니다. " + approverId);
            }
        }

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

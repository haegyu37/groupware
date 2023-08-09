package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.LineDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

        Long maxLineId = approvalRepository.findMaxLineId();
        if (maxLineId == null) {
            maxLineId = 1L;
        } else {
            maxLineId = maxLineId + 1;
        }

        List<Long> approvers = lineDTO.getApprovers();
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        if (currentMemberId != null) {
            approvers.add(0, currentMemberId);
        } else {
            System.out.println("로그인 아이디가  null : " + currentMemberId);
        }

        int lastIndex = lineDTO.getApprovers().size() - 1; // 배열의 맨 마지막 인덱스

        for (int i = 0; i < lineDTO.getApprovers().size(); i++) {
            Long approverId = lineDTO.getApprovers().get(i);
            if (approverId != null) {
                Approval approval = new Approval();
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


    //모든 결재라인 -> 저장한거 다 뜨게
    @GetMapping("/list")
    public Map<Long, List<Approval>> groupApprovalsByLineId() {
        List<Approval> allApprovals = approvalRepository.findAll();
        return Approval.groupByLineId(allApprovals);
    }

    //내 결재라인 -> 토큰값 기준
    @GetMapping("/mylist")
    public Map<Long, List<Approval>> getMyLines() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //id를 기준으로 Approval을 찾는 메소드
        List<Approval> myApproval = approvalRepository.findByWriter(currentMemberId);
        return Approval.groupByLineId(myApproval);

    }

    //결재라인 조회
    @GetMapping("/{id}")
    public Map<Long, List<Approval>> getApprovalLineById(@PathVariable Long id) {
        return Approval.groupByLineId(lineService.getLineByLineId(id));

    }

    //결재라인 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        lineService.deleteDocumentByLineId(id);
    }


}

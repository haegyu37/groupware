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

import java.util.ArrayList;
import java.util.HashMap;
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

        List<Long> approvers = lineDTO.getApprovers();
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        approvers.add(0, currentMemberId);

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

                if (approvers.size() == 4) {
                    // 맨 마지막 인덱스인 경우 refer를 "참조"로 설정
                    if (i == approvers.size() - 1) {
                        approval.setRefer("참조");
                    }
                }

                approvalRepository.save(approval);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("직원을 찾을 수 없습니다. " + approverId);
            }
        }

        return ResponseEntity.ok("결재라인을 저장했습니다.");
    }

    //먼가 정리된 내 결재라인 목록 (멤버 정보 뜸)
    @GetMapping("/list")
    public Map<Long, List<Map<String, Object>>> getMemberLineById() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();

        // 모든 결재 정보를 데이터베이스에서 조회 All 전부 다
        List<Approval> allApprovals = approvalRepository.findByWriter(currentMemberId);
        // 그룹화된 결재 정보를 담을 Map 생성
        Map<Long, List<Map<String, Object>>> groupedApprovals = new HashMap<>();

        // 모든 결재 정보를 순회하며 그룹화 작업 수행
        for (Approval approval : allApprovals) {
            // 결재 정보에서 라인(Line)의 ID를 가져옴
            Long lineId = approval.getLineId();
            // lineId가 null이 아닐 때만 그룹화 작업을 수행함 (null인 경우는 건너뜀)
            if (lineId != null) {
                // 결재 정보를 담을 Map 생성
                Map<String, Object> approvalInfo = new HashMap<>();
                // 결재 정보에서 필요한 데이터를 approvalInfo Map에 담음
                approvalInfo.put("lineName", approval.getName());
                approvalInfo.put("lineId", approval.getLineId());

                // 멤버 정보 추가
                // 결재 정보에 포함된 멤버 ID를 사용하여 멤버 정보를 조회
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    // 멤버 정보를 approvalInfo Map에 담음
                    approvalInfo.put("no", memberInfo.getNo());
                    approvalInfo.put("name", memberInfo.getName());
                    approvalInfo.put("team", memberInfo.getTeam());
                    approvalInfo.put("position", memberInfo.getPosition());
                }

                // 이미 해당 라인에 결재 정보가 있는 경우
                if (groupedApprovals.containsKey(lineId)) {
                    // 해당 라인에 결재 정보를 추가함
                    groupedApprovals.get(lineId).add(approvalInfo);
                } else {
                    // 해당 라인에 결재 정보가 없는 경우 새로운 리스트를 생성하여 추가함
                    List<Map<String, Object>> lineApprovals = new ArrayList<>();
                    lineApprovals.add(approvalInfo);
                    groupedApprovals.put(lineId, lineApprovals);
                }
            }
        }

        // 그룹화된 결재 정보를 반환
        return groupedApprovals;
    }


    //결재라인 조회
    @GetMapping("/{id}")
    public Map<String, List<Map<String, Object>>> getMemberInfoLineById(@PathVariable Long id) {
        List<Approval> approvals = lineService.getByLineId(id); //lineId로 결재 찾아
        Map<String, List<Map<String, Object>>> groupedApprovals = new HashMap<>();
        List<Map<String, Object>> lineApprovals = new ArrayList<>();

        for (Approval approval : approvals) {
            Long lineId = approval.getLineId();
            String name = approval.getName();

            if (lineId != null) { // lineId가 null인 경우는 건너뜀
                Map<String, Object> approvalInfo = new HashMap<>();

                // 멤버 정보 추가
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    approvalInfo.put("lineId", approval.getLineId());
                    approvalInfo.put("id", memberInfo.getId());
                    approvalInfo.put("no", memberInfo.getNo());
                    approvalInfo.put("name", memberInfo.getName());
                    approvalInfo.put("team", memberInfo.getTeam());
                    approvalInfo.put("position", memberInfo.getPosition());
                }

                // 이미 해당 라인에 결재 정보가 있는 경우
                if (groupedApprovals.containsKey(lineId)) {
                    groupedApprovals.get(lineId).add(approvalInfo);
                } else {
                    lineApprovals.add(approvalInfo);
                    groupedApprovals.put(name, lineApprovals);
                }
            }


        }
        return groupedApprovals;

    }

    //결재라인 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        lineService.deleteDocumentByLineId(id);
    }


}

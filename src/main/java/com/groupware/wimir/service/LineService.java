package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LineService {

    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private MemberRepository memberRepository;


    public void deleteDocumentByLineId(Long id) {
        approvalRepository.deleteByLineId(id);
    }

    //라인아이디로 찾기
    public List<Approval> getByLineId(Long id) {
        List<Approval> lines = approvalRepository.findByLineId(id);
        return lines;
    }

    //lineId를 기준으로 그룹화
    public Map<Long, List<Map<String, Object>>> getGroupedApprovals(List<Approval> approvals) {
        Map<Long, List<Map<String, Object>>> groupedApprovals = new HashMap<>();

        for (Approval approval : approvals) {
            Long lineId = approval.getLineId();

            if (lineId != null) {
                // 각 lineId에 대한 리스트가 없으면 초기화하도록 합니다
                groupedApprovals.putIfAbsent(lineId, new ArrayList<>());

                Map<String, Object> approvalInfo = new HashMap<>();
                approvalInfo.put("lineId", lineId); // lineId 정보 추가

                // Retrieve member information
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    approvalInfo.put("lineName", approval.getName());
                    approvalInfo.put("id", memberInfo.getId());
                    approvalInfo.put("name", memberInfo.getName());
                    approvalInfo.put("team", memberInfo.getTeam());
                    approvalInfo.put("position", memberInfo.getPosition());
                }

                // 그룹에 결재 정보를 추가합니다
                groupedApprovals.get(lineId).add(approvalInfo);
            }
        }

        return groupedApprovals;
    }

    //line name을 기준으로 그룹화
    public Map<String, List<Map<String, Object>>> getGroupedApprovalsName(List<Approval> approvals) {
        Map<String, List<Map<String, Object>>> groupedApprovals = new HashMap<>();

        for (Approval approval : approvals) {
            String name = approval.getName();

            if (name != null) {
                // 각 lineId에 대한 리스트가 없으면 초기화하도록 합니다
                groupedApprovals.putIfAbsent(name, new ArrayList<>());

                Map<String, Object> approvalInfo = new HashMap<>();
                approvalInfo.put("name", name); // lineId 정보 추가

                // Retrieve member information
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    approvalInfo.put("lineId", approval.getLineId());
                    approvalInfo.put("id", memberInfo.getId());
                    approvalInfo.put("name", memberInfo.getName());
                    approvalInfo.put("team", memberInfo.getTeam());
                    approvalInfo.put("position", memberInfo.getPosition());
                }

                // 그룹에 결재 정보를 추가합니다
                groupedApprovals.get(name).add(approvalInfo);
            }
        }

        return groupedApprovals;
    }

    public Map<Long, List<Map<String, Object>>> getGroupedApprovalsDoc(List<Approval> approvals) {
        Map<Long, List<Map<String, Object>>> groupedApprovals = new HashMap<>();

        for (Approval approval : approvals) {
            Document document = approval.getDocument();

            if (document != null) {
                Long documentId = document.getId(); // Document 객체의 id를 키로 사용
                Map<String, Object> approvalInfo = new HashMap<>();

                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    approvalInfo.put("no", memberInfo.getNo()); // 직원 사번
                    approvalInfo.put("name", memberInfo.getName()); // 직원 이름
                    approvalInfo.put("team", memberInfo.getTeam()); // 직원 부서
                    approvalInfo.put("position", memberInfo.getPosition()); // 직원 직급
                }

                approvalInfo.put("lineId", approval.getLineId()); // 즐찾라인 아이디
                approvalInfo.put("lineName", approval.getName()); // 즐찾라인 이름
                approvalInfo.put("current", approval.getCurrent()); // 현재 결재 순서인지
                approvalInfo.put("status", approval.getStatus()); // 결재 결과
                approvalInfo.put("appDate", approval.getAppDate()); // 결재일

                groupedApprovals.putIfAbsent(documentId, new ArrayList<>());
                groupedApprovals.get(documentId).add(approvalInfo);
            }
        }

        return groupedApprovals;
    }
    //취소를 위한 결재정보
    public Map<String, Object> appInfoForCancel(List<Approval> approvals, Long id) {

        List<Long> memberIds = approvals.stream()
                .map(Approval::getMemberId)
                .collect(Collectors.toList());

        int memberIndex = -1; // 초기값을 -1로 설정

        // 결재자 리스트 중에서 현재 결재자 아이디 가져오기
        for (int i = 0; i < memberIds.size(); i++) {
            if (memberIds.get(i) == id) {
                memberIndex = i; // 일치하는 값이 발견되면 memberIndex를 설정하고
                break; // 루프를 종료합니다.
            }
        }
        Map<String, Object> appInfoForCancel = new HashMap<>();

        Approval currentApprover = approvals.get(memberIndex);
        if(memberIndex != memberIds.size() - 1) {
            Approval nextApprover = approvals.get(memberIndex + 1);
            appInfoForCancel.put("nextStauts", nextApprover.getStatus());
        } else {
            appInfoForCancel.put("nextStatus", null);
        }

        appInfoForCancel.put("myCurrent", currentApprover.getCurrent());
        appInfoForCancel.put("myStatus", currentApprover.getStatus());

        return appInfoForCancel;
    }

}

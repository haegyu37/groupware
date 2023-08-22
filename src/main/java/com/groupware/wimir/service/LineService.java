package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
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

    public List<Approval> getLineByLineId(Long id) {
        return approvalRepository.findByLineId(id);
    }

    //     Document ID에 해당하는 모든 Approval의 Member ID를 리스트로 가져오는 메서드
    public List<Long> getMemberIdsByDocumentId(Long documentId) {
        List<Approval> approvals = approvalRepository.findByDocument(documentId); //document로 approval 리스트 만듦
        List<Long> memberIds = new ArrayList<>();

        for (Approval approval : approvals) {
            memberIds.add(approval.getMemberId()); //memberId만 찾아서 리스트 만들어줌
        }

        return memberIds;
    }

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

    //document를 기준으로 그룹화
    public Map<Long, List<Map<String, Object>>> getGroupedApprovalsDoc(List<Approval> approvals) {
        Map<Long, List<Map<String, Object>>> groupedApprovals = new HashMap<>();

        for (Approval approval : approvals) {
            Long document = approval.getDocument();

            if (document != null) {
                // 각 lineId에 대한 리스트가 없으면 초기화하도록 합니다
                groupedApprovals.putIfAbsent(document, new ArrayList<>());

                Map<String, Object> approvalInfo = new HashMap<>();
//                approvalInfo.put("document", document); // lineId 정보 추가

                // Retrieve member information
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
//                    approvalInfo.put("lineId", approval.getLineId());
                    approvalInfo.put("no", memberInfo.getNo()); //직원 사번
                    approvalInfo.put("name", memberInfo.getName()); //직원 이름
                    approvalInfo.put("team", memberInfo.getTeam()); //직원 부서
                    approvalInfo.put("position", memberInfo.getPosition()); //직원 직급
                    approvalInfo.put("lineId", approval.getLineId()); //즐찾라인 아이디
                    approvalInfo.put("lineName", approval.getName()); //즐찾라인 이름
                    approvalInfo.put("current", approval.getCurrent()); //현재 결재 순서인지
                    approvalInfo.put("status", approval.getStatus()); //결재 결과
                    approvalInfo.put("appDate", approval.getAppDate()); //결재일

                }

                // 그룹에 결재 정보를 추가합니다
                groupedApprovals.get(document).add(approvalInfo);
            }
        }

        return groupedApprovals;
    }

    //dno로 결재 불러오기
    public List<Approval> getByDocument(Long id) {
        List<Approval> approvals = approvalRepository.findByDocument(id); //document로 approval 리스트 만듦
        return approvals;
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

        Approval currentApprover = approvals.get(memberIndex);
        Approval nextApprover = approvals.get(memberIndex + 1);

        Map<String, Object> appInfoForCancel = new HashMap<>();
        appInfoForCancel.put("nextStauts", nextApprover.getStatus());
        appInfoForCancel.put("myCurrent", currentApprover.getCurrent());
        appInfoForCancel.put("myStatus", currentApprover.getStatus());

        return appInfoForCancel;
    }

}

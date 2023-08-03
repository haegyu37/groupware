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

    public List<Long> getMemberIdByLineId(Long id) {
        Map<Long, List<Approval>> lines = Approval.groupByLineId(approvalRepository.findByLineId(id));
        System.out.println("이름" + lines);

        List<Long> memberIds = lines.values() // values()를 사용하여 List<Approval> 컬렉션을 얻음
                .stream()
                .flatMap(approvals -> approvals.stream().map(Approval::getMemberId))
                .collect(Collectors.toList());

        return memberIds;
    }


    //     Document ID에 해당하는 모든 Approval의 Member ID를 리스트로 가져오는 메서드
    public List<Approval> getByDocument(Long id) {
        List<Approval> approvals = approvalRepository.findByDocument(id); //document로 approval 리스트 만듦

        return approvals;
    }

    public void deleteDocumentByLineId(Long id) {
        approvalRepository.deleteByLineId(id);
    }

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
                    approvalInfo.put("no", memberInfo.getNo());
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
                    approvalInfo.put("no", memberInfo.getNo());
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
                approvalInfo.put("document", document); // lineId 정보 추가

                // Retrieve member information
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    approvalInfo.put("lineId", approval.getLineId());
                    approvalInfo.put("lineName", approval.getName());
                    approvalInfo.put("no", memberInfo.getNo());
                    approvalInfo.put("name", memberInfo.getName());
                    approvalInfo.put("team", memberInfo.getTeam());
                    approvalInfo.put("position", memberInfo.getPosition());
                }

                // 그룹에 결재 정보를 추가합니다
                groupedApprovals.get(document).add(approvalInfo);
            }
        }

        return groupedApprovals;
    }


}

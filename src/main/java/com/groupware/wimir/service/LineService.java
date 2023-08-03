package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.LineDTO;
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

    //결재라인 저장
    public void saveApprovalLine(LineDTO lineDTO) {
        Long maxLineId = approvalRepository.findMaxLineId();
        if (maxLineId == null) {
            maxLineId = 1L;
        } else {
            maxLineId = maxLineId + 1;
        }

        List<Long> curAppList = lineDTO.getApprovers();
        curAppList.add(0, SecurityUtil.getCurrentMemberId());

        int lastIndex = curAppList.size() - 1; // Last index of the array

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
                throw new IllegalArgumentException("직원을 찾을 수 없습니다. " + approverId);
            }
        }
    }

    //document로 approval 찾기
    public List<Approval> getByDocument(Long id) {
        List<Approval> approvals = approvalRepository.findByDocument(id); //document로 approval 리스트 만듦

        return approvals;
    }

    //라인아이디를 찾아서 결재라인 삭제
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
                approvalInfo.put("document", document); // lineId 정보 추가

                // Retrieve member information
                Member memberInfo = memberRepository.findById(approval.getMemberId()).orElse(null);
                if (memberInfo != null) {
                    approvalInfo.put("lineId", approval.getLineId());
                    approvalInfo.put("lineName", approval.getName());
                    approvalInfo.put("id", memberInfo.getId());
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

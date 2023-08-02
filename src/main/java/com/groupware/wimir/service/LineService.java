package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LineService {

    @Autowired
    private ApprovalRepository approvalRepository;

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
    public List<Long> getMemberIdsByDocumentId(Long documentId) {
        List<Approval> approvals = approvalRepository.findByDocument(documentId); //document로 approval 리스트 만듦
        List<Long> memberIds = new ArrayList<>();

        for (Approval approval : approvals) {
            memberIds.add(approval.getMemberId()); //memberId만 찾아서 리스트 만들어줌
        }

        return memberIds;
    }

//    //    line의 memberId로 직원 정보도 함께 불러오기
//    public List<Approval> getMemberInfoByline(Long id) {
//
//    }

    public void deleteDocumentByLineId(Long id) {
        approvalRepository.deleteByLineId(id);
    }

    public List<Approval> getByLineId(Long id) {
        List<Approval> lines = approvalRepository.findByLineId(id);
        return lines;
    }
}

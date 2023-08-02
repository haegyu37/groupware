package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LineService {

    @Autowired
    private ApprovalRepository approvalRepository;

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

}

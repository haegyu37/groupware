package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
//import com.groupware.wimir.repository.LineRepository;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

//     Document ID에 해당하는 모든 Approval의 Member ID를 리스트로 가져오는 메서드
    public List<Long> getMemberIdsByDocumentId(Long documentId) {
        List<Approval> approvals = approvalRepository.findByDocument(documentId); //document로 approval 리스트 만듦
        List<Long> memberIds = new ArrayList<>();

        for (Approval approval : approvals) {
            memberIds.add(approval.getMemberId()); //memberId만 찾아서 리스트 만들어줌
        }

        return memberIds;
    }

}

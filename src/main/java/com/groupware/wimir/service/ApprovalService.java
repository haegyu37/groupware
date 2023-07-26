package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {

    @Autowired
    private ApprovalRepository approvalRepository;

    public List<Long> saveApprovalIds(Approval approval) {
        return approvalRepository.save(approval).getApproverIds();
    }

    public List<Approval> getAllApprovals() {
        return approvalRepository.findAll();
    }






}

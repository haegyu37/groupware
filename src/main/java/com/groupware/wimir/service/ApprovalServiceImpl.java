package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.repository.ApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApprovalServiceImpl implements ApprovalService{

    private final ApprovalRepository approvalRepository;

    @Autowired
    public ApprovalServiceImpl(ApprovalRepository approverRepository) {
        this.approvalRepository = approverRepository;
    }

    @Override
    public Approval saveApprover(Approval approver) {
        return approvalRepository.save(approver);
    }
}

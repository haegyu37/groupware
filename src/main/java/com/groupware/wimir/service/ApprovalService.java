package com.groupware.wimir.service;

import com.groupware.wimir.entity.Approval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ApprovalService.java
public interface ApprovalService {
    Approval saveApprover(Approval approver);
}



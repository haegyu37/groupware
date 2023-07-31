package com.groupware.wimir.service;

//import com.groupware.wimir.DTO.ApprovalRequestDTO;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ApprovalDTO approvalDTO;
//    @Autowired
//    private ApprovalRequestDTO approvalRequestDTO;

//    public List<Approval> getAllApprovals() {
//        return approvalRepository.findAll();
//    }

//    public Optional<Approval> getApprovalLineById(Long id) {
//        return approvalRepository.findById(id);
//    }

//    public Document getApprovalDocumentByDocumentId(Long id) {
//        return documentService.findDocumentById(id);
//    }
}

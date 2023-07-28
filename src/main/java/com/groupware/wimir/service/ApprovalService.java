package com.groupware.wimir.service;

//import com.groupware.wimir.DTO.ApprovalRequestDTO;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.ApprovalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;
//    @Autowired
//    private DocumentService documentService;
//    @Autowired
//    private ApprovalDTO approvalDTO;

//    public void submitApproval(DocumentDTO documentDTO) {
////        Long documentId = documentDTO.getDocumentId();
////        Long approverId = requestDTO.getApproverId();
//        int result = documentDTO.getResult();
//
//                // 여기서 결재 처리 로직을 구현하면 됩니다.
//                // 예를 들어, 결재자 ID로 사용자를 찾고 결재 여부에 따라 문서의 상태를 업데이트하거나 다음 단계로 전달하는 등의 작업을 수행합니다.
//                // 이 예시에서는 단순히 메시지를 출력하는 것으로 대체합니다.
//
//        String approvalStatus = approved ? "승인" : "반려";
//        System.out.println(approverId + "가 문서 ID " + documentId + "에 대해 " + approvalStatus + " 처리했습니다.");
//    }

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

    //결재 전송
    public ResponseEntity<Approval> setApproval(DocumentDTO documentDTO) {
        Approval savedApproval = null;
        System.out.println("즐찾2"+documentDTO.getLineId());
        System.out.println("문서번호1`"+documentDTO.getId());

        if(documentDTO.getLineId() !=  null) {
            System.out.println("즐찾3"+documentDTO.getLineId());
            List<Approval> approvals = approvalRepository.findByLineId(documentDTO.getLineId());

            List<Long> memberIds = approvals.stream().map(Approval::getMemberId).collect(Collectors.toList());
            List<Long> writers = approvals.stream().map(Approval::getWriter).collect(Collectors.toList());
            List<String> names = approvals.stream().map(Approval::getName).collect(Collectors.toList());
            // 다른 필드들에 대해서도 필요한 경우에 리스트로 추출

            // 리스트로 만들어진 각 칼럼들의 값들을 approval 엔티티에 삽입
            for (int i = 0; i < approvals.size(); i++) {
                System.out.println("즐찾5");
                Approval approval = new Approval();
                approval.setMemberId(memberIds.get(i));
                approval.setWriter(writers.get(i));
                approval.setName(names.get(i));
                approval.setDocument(documentDTO.getId());

//                approval.setLineId(documentDTO.getLineId());

                savedApproval = approvalRepository.save(approval);
                System.out.println("즐찾6");

//                return ResponseEntity.ok(savedApproval);


            }
            return ResponseEntity.ok(savedApproval);
//            System.out.println("즐찾4:  "+ savedApproval);
        } else { //결재자 각각 지정해서 삽입

            for(Long approverId : documentDTO.getApprovers()){
                Approval approval = new Approval();
                approval.setDocument(documentDTO.getId());
                approval.setMemberId(approverId);

                savedApproval = approvalRepository.save(approval);
                System.out.println("각각: "+ savedApproval);
            }

        }
        return ResponseEntity.ok(savedApproval);
    }


}

package com.groupware.wimir.service;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private ApprovalService approvalService;

    //결재 전송
    public ResponseEntity<Approval> setApproval(DocumentDTO documentDTO) {
        Approval savedApproval = null;

        Long maxDocId = documentRepository.findMaxDocId(); // DB에서 문서아이디의 최대값을 가져옴
        if(maxDocId == null) {
            maxDocId = 1L;
        } else {
            maxDocId = maxDocId + 1;
        }


        if(documentDTO.getLineId() !=  null) {
            List<Approval> approvals = approvalRepository.findByLineId(documentDTO.getLineId());

            List<Long> memberIds = approvals.stream().map(Approval::getMemberId).collect(Collectors.toList());
            List<Long> writers = approvals.stream().map(Approval::getWriter).collect(Collectors.toList());
            List<String> names = approvals.stream().map(Approval::getName).collect(Collectors.toList());
            // 다른 필드들에 대해서도 필요한 경우에 리스트로 추출

            // 리스트로 만들어진 각 칼럼들의 값들을 approval 엔티티에 삽입
            for (int i = 0; i < approvals.size(); i++) {
                Approval approval = new Approval();
                approval.setMemberId(memberIds.get(i));
                approval.setWriter(writers.get(i));
                approval.setName(names.get(i));
                approval.setDocument(maxDocId);

                savedApproval = approvalRepository.save(approval);

            }
            return ResponseEntity.ok(savedApproval);
        } else { //결재자 각각 지정해서 삽입

            for(Long approverId : documentDTO.getApprovers()){
                Approval approval = new Approval();
                approval.setDocument(maxDocId);
                approval.setMemberId(approverId);

                savedApproval = approvalRepository.save(approval);
            }

        }
        return ResponseEntity.ok(savedApproval);
    }

    //내 결재 리스트
    public List<Document> getApprovals(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Long> docIds = new ArrayList<>();

        for (Approval approval : approvals) {
            Long docId = approval.getDocument(); //Approval에서 document만 찾음
            if (docId != null) {
                docIds.add(docId); //docIds 리스트에 추가
            } else {
                continue; //null이면 건너뜀
            }
        }

        List<Document> myAppDocs = new ArrayList<>(); // myAppDocs 리스트를 초기화

        for (Long docId : docIds) {
            Document document = documentRepository.findById(docId).orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocs.add(document); //Document 리스트에 추가
            }
        }

        return myAppDocs; // 리스트 반환
    }

    // Document ID에 해당하는 모든 Approval의 Member ID를 리스트로 가져오는 메서드
    public List<Long> getMemberIdsByDocumentId(Long documentId) {
        List<Approval> approvals = approvalRepository.findByDocumentId(documentId); //document로 approval 리스트 만듦
        List<Long> memberIds = new ArrayList<>();

        for (Approval approval : approvals) {
            memberIds.add(approval.getMemberId()); //memberId만 찾아서 리스트 만들어줌
        }

        return memberIds;
    }

//    //결재 승인
//    public void updateApprovalStatus(Long documentId, Long currentMemberId) {
//        List<Long> approvers = approvalService.getMemberIdsByDocumentId(documentId); //문서에 해당된 결재자 memberId 리스트
//
////        // 문서 아이디에 해당하는 승인 목록을 찾아서 lineId와 appDate 기준으로 정렬
////        List<Approval> documentApprovals = approvals.stream()
////                .filter(approval -> approval.getDocument().equals(documentId))
////                .sorted(Comparator.comparing(Approval::getLineId).thenComparing(Approval::getAppDate))
////                .collect(Collectors.toList());
//
////        boolean foundCurrent = false;
//
//        // 정렬된 승인 목록을 순회하며 현재 결재자의 다음 결재자를 찾고 current 상태를 업데이트
//        for (int i = 0; i < approvers.size(); i++) {
//            if (foundCurrent) {
//                // 다음 결재자의 current 상태를 "Y"로 설정
//                approval.setCurrent("Y");
//                approvalRepository.save(approval);
//                break;
//            }
//
//            // 현재 결재자를 찾아서 상태를 승인(1)으로 변경하고 foundCurrent를 true로 설정
//            if (approval.getMemberId().equals(currentMemberId)) {
//                approval.setStatus(1);
//                foundCurrent = true;
//                approvalRepository.save(approval);
//            }
//        }
//    }
//
//    //결재 반려
//    public void rejectDocument(Document document, Member currentApprover) {
//        int status = document.getStatus();
//        if (status == 0) {
//            // 현재 문서가 결재 중인 경우에만 반려 처리
//            document.setStatus(2); // 반려 상태로 변경
//            document.setLastApprovedBy(currentApprover); // 반려한 멤버 설정
//
//            document.setCurrentApprover(null); // 다음 담당자 없음 (결재 종료)
//
//            // 반려 처리 시 추가로 해야할 작업이 있다면 여기에 추가
//            // 예: 알림 메일 발송, 반려 사유 입력 등
//
//            documentRepository.save(document); // 변경 사항 저장
//        } else {
//            // 이미 승인 또는 반려된 문서인 경우 처리할 내용 추가
//            // 예: 이미 반려된 문서에 대한 예외 처리, 알림 메시지 등
//        }
//    }


}

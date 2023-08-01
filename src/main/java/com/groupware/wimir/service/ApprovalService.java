//package com.groupware.wimir.service;
//
//import com.groupware.wimir.DTO.ApprovalDTO;
//import com.groupware.wimir.DTO.DocumentDTO;
//import com.groupware.wimir.entity.Approval;
////import com.groupware.wimir.entity.ApprovalLine;
//import com.groupware.wimir.entity.Document;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.repository.ApprovalRepository;
//import com.groupware.wimir.repository.DocumentRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//import static java.time.LocalDateTime.now;
//
//@Service
//@RequiredArgsConstructor
//@Transactional
//public class ApprovalService {
//    @Autowired
//    private ApprovalRepository approvalRepository;
//    @Autowired
//    private DocumentRepository documentRepository;
//    @Autowired
//    private LineService lineService;
//
////    //결재 전송
////    public ResponseEntity<Approval> setApproval(DocumentDTO documentDTO) {
////        Approval savedApproval = null;
////
////        Long maxDocId = documentRepository.findMaxDocId(); // DB에서 문서아이디의 최대값을 가져옴
////        if(maxDocId == null) {
////            maxDocId = 1L;
////        } else {
////            maxDocId = maxDocId + 1;
////        }
////
////
////        if(documentDTO.getLineId() !=  null) {
////            List<Approval> approvals = approvalRepository.findByLineId(documentDTO.getLineId());
////
////            List<Long> memberIds = approvals.stream().map(Approval::getMemberId).collect(Collectors.toList());
////            List<Long> writers = approvals.stream().map(Approval::getWriter).collect(Collectors.toList());
////            List<String> names = approvals.stream().map(Approval::getName).collect(Collectors.toList());
////            // 다른 필드들에 대해서도 필요한 경우에 리스트로 추출
////
////            // 리스트로 만들어진 각 칼럼들의 값들을 approval 엔티티에 삽입
////            for (int i = 0; i < approvals.size(); i++) {
////                Approval approval = new Approval();
////                approval.setMemberId(memberIds.get(i));
////                approval.setWriter(writers.get(i));
////                approval.setName(names.get(i));
////                approval.setDocument(maxDocId);
////
////                savedApproval = approvalRepository.save(approval);
////
////            }
////            return ResponseEntity.ok(savedApproval);
////        } else { //결재자 각각 지정해서 삽입
////
////            for(Long approverId : documentDTO.getApprovers()){
////                Approval approval = new Approval();
////                approval.setDocument(maxDocId);
////                approval.setMemberId(approverId);
////
////                savedApproval = approvalRepository.save(approval);
////            }
////
////        }
////        return ResponseEntity.ok(savedApproval);
////    }
//
//
//    //결재 전송
//    public ResponseEntity<Approval> setApproval(DocumentDTO documentDTO) {
//        Approval savedApproval = null;
//
//        Long maxDocId = documentRepository.findMaxDocId(); // DB에서 문서아이디의 최대값을 가져옴
//        if (maxDocId == null) {
//            maxDocId = 1L;
//        } else {
//            maxDocId = maxDocId + 1;
//        }
//
//        if (documentDTO.getLineId() != null) {
//            List<Approval> approvals = approvalRepository.findByLineId(documentDTO.getLineId());
//
//            List<Long> memberIds = approvals.stream().map(Approval::getMemberId).collect(Collectors.toList());
//            List<Long> writers = approvals.stream().map(Approval::getWriter).collect(Collectors.toList());
//            List<String> names = approvals.stream().map(Approval::getName).collect(Collectors.toList());
//            // 다른 필드들에 대해서도 필요한 경우에 리스트로 추출
//
//            // 리스트로 만들어진 각 칼럼들의 값들을 approval 엔티티에 삽입
//            for (int i = 0; i < approvals.size(); i++) {
//                Approval approval = new Approval();
//                approval.setMemberId(memberIds.get(i));
//                approval.setWriter(writers.get(i));
//                approval.setName(names.get(i));
//                approval.setDocument(maxDocId);
//
//                if (i == 0) {
//                    approval.setCurrent("Y"); // 첫 번째 결재자인 경우 current를 'Y'로 설정
//                } else {
//                    approval.setCurrent("N"); // 그 외의 결재자는 current를 'N'으로 설정
//                }
//
//                savedApproval = approvalRepository.save(approval);
//            }
//            return ResponseEntity.ok(savedApproval);
//        } else { //결재자 각각 지정해서 삽입
//            List<Long> approvers = documentDTO.getApprovers();
//            for (int i = 0; i < approvers.size(); i++) {
//                Long approverId = approvers.get(i);
//                Approval approval = new Approval();
//                approval.setDocument(maxDocId);
//                approval.setMemberId(approverId);
//
//                if (i == 0) {
//                    approval.setCurrent("Y"); // 첫 번째 결재자인 경우 current를 'Y'로 설정
//                } else {
//                    approval.setCurrent("N"); // 그 외의 결재자는 current를 'N'으로 설정
//                }
//
//                savedApproval = approvalRepository.save(approval);
//            }
//
//        }
//        return ResponseEntity.ok(savedApproval);
//    }
//
//    //내 결재 리스트
//    public List<Document> getApprovals(Long id) {
//        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
//        List<Long> docIds = new ArrayList<>();
//
//        for (Approval approval : approvals) {
//            Long docId = approval.getDocument(); //Approval에서 document만 찾음
//            if (docId != null) {
//                docIds.add(docId); //docIds 리스트에 추가
//            } else {
//                continue; //null이면 건너뜀
//            }
//        }
//
//        List<Document> myAppDocs = new ArrayList<>(); // myAppDocs 리스트를 초기화
//
//        for (Long docId : docIds) {
//            Document document = documentRepository.findById(docId)
//                    .orElse(null); //id로 Document 찾음
//            if (document != null) {
//                myAppDocs.add(document); //Document 리스트에 추가
//            }
//        }
//
//        return myAppDocs; // 리스트 반환
//    }
//
//    //결재승인
//    public void approveDocument(Long documentId) {
//        List<Approval> approvals = approvalRepository.findByDocument(documentId);
//
//        for (int i = 0; i < approvals.size(); i++) {
//            Approval approval = approvals.get(i);
//
//            if (approval.getCurrent().equals("Y")) {
//                // 현재 결재자를 찾았을 경우
//                approval.setAppDate(now());
//                approval.setStatus(1);
//                approval.setCurrent("N");
//
//                // 다음 결재자가 있을 경우 current를 Y로 지정
//                if (i + 1 < approvals.size()) {
//                    Approval nextApproval = approvals.get(i + 1);
//                    nextApproval.setCurrent("Y");
//                } else {
//                    // 다음 결재자가 없는 경우, 즉 리스트의 마지막 결재자인 경우
//                    // document의 result를 1로 set
//                    Optional<Document> documentOptional = documentRepository.findById(documentId);
//                    Document document = documentOptional.orElse(null);
//
//                    if (document != null) {
//                        document.setResult(1);
//                        document.setAppDate(LocalDateTime.now());
//                        documentRepository.save(document);
//                    }
//                }
//                break;
//            }
//        }
//    }
//
//
////
////    //결재 반려
//public void rejectDocument(ApprovalDTO approvalDTO, Long documentId) {
//    List<Approval> approvals = approvalRepository.findByDocument(documentId);
//
//    for (int i = 0; i < approvals.size(); i++) {
//        Approval approval = approvals.get(i);
//
//        if (approval.getCurrent().equals("Y")) {
//            // 현재 결재자를 찾았을 경우
//            approval.setAppDate(now());
//            approval.setStatus(2);
//            approval.setCurrent("N");
//            approval.setReason(approvalDTO.getReason());
//
//                Optional<Document> documentOptional = documentRepository.findById(documentId);
//                Document document = documentOptional.orElse(null);
//
//                if (document != null) {
//                    document.setResult(2);
//                    document.setAppDate(LocalDateTime.now());
//                    documentRepository.save(document);
//                }
//            break;
//        }
//    }
//}
//
//
//
//
//}

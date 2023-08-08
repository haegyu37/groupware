package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
@Transactional
public class ApprovalService {
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    DocumentService documentService;


    //결재 요청 -> 저장
    public ResponseEntity<Approval> setApproval(DocumentDTO documentDTO) {
        Approval savedApproval = null;

        Long maxDno = documentRepository.findMaxDno(); // DB에서 저장번호 최댓값
        if (maxDno == null) {
            maxDno = 1L;
        } else {
            maxDno = maxDno + 1;
        }

        if (documentDTO.getLineId() != null) {
            List<Approval> approvals = approvalRepository.findByLineId(documentDTO.getLineId());

            List<Long> memberIds = approvals.stream().map(Approval::getMemberId).collect(Collectors.toList());
            List<Long> writers = approvals.stream().map(Approval::getWriter).collect(Collectors.toList());
            List<String> names = approvals.stream().map(Approval::getName).collect(Collectors.toList());
            List<String> refers = approvals.stream().map(Approval::getRefer).collect(Collectors.toList());
            // 다른 필드들에 대해서도 필요한 경우에 리스트로 추출

            // 리스트로 만들어진 각 칼럼들의 값들을 approval 엔티티에 삽입
            for (int i = 0; i < approvals.size(); i++) {
                Approval approval = new Approval();
                approval.setMemberId(memberIds.get(i));
                approval.setWriter(writers.get(i));
                approval.setName(names.get(i));
                approval.setRefer(refers.get(i));
                approval.setDocument(maxDno);

                if (i == 0) {
                    approval.setCurrent("Y"); // 첫 번째 결재자인 경우 current를 'Y'로 설정
                } else {
                    approval.setCurrent("N"); // 그 외의 결재자는 current를 'N'으로 설정
                }

                savedApproval = approvalRepository.save(approval);
            }
            return ResponseEntity.ok(savedApproval);
        } else { //결재자 각각 지정해서 삽입

            int lastIndex = documentDTO.getApprovers().size();
            List<Long> approvers = documentDTO.getApprovers();
            approvers.add(0, SecurityUtil.getCurrentMemberId());

            for (int i = 0; i < approvers.size(); i++) {
                Long approverId = approvers.get(i);
                Approval approval = new Approval();
                approval.setDocument(maxDno);
                approval.setMemberId(approverId);
                approval.setRefer("결재");

                // 맨 마지막 인덱스인 경우 refer를 "참조"로 설정
                if (i == lastIndex) {
                    approval.setRefer("참조");
                }

                if (i == 0) {
                    approval.setCurrent("Y"); // 첫 번째 결재자인 경우 current를 'Y'로 설정
                } else {
                    approval.setCurrent("N"); // 그 외의 결재자는 current를 'N'으로 설정
                }

                savedApproval = approvalRepository.save(approval);
            }

        }
        return ResponseEntity.ok(savedApproval);
    }

    //내가 결재라인인 문서 리스트 all
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
            Document document = documentRepository.findByDno(docId);
//                    .orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocs.add(document); //Document 리스트에 추가
            }
        }

        return myAppDocs; // 리스트 반환
    }

    //내 결재 리스트 근데 이제 내가 결재할 차례인 .. 그것들 리스트
    public List<Document> getApprovalsNow(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //내 아이디가 있는 결재 찾기

        List<Document> myAppDocs = approvals.stream()
                .filter(approval -> "Y".equals(approval.getCurrent())) //그 중에서 내 차례인거
                .map(approval -> documentRepository.findByDno(approval.getDocument())) //그 중에서 document 아이디로 문서 찾기
//                .filter(Optional::isPresent) //객체의 값이 존재하는지 확인, 값이 존재하는 경우에만 스트림에 남김
//                .map(Optional::get)
                .collect(Collectors.toList());

        return myAppDocs;
    }


    //내가 결재 완 리스트 all
    public List<Document> getApproved(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Approval> approved = approvals.stream()
                .filter(approval -> approval.getStatus() != 0 && approval.getAppDate() != null)
                .collect(Collectors.toList());
        List<Long> docIds = new ArrayList<>();

        for (Approval approval : approved) {
            Long docId = approval.getDocument(); //Approval에서 document만 찾음
            if (docId != null) {
                docIds.add(docId); //docIds 리스트에 추가
            } else {
                continue; //null이면 건너뜀
            }
        }

        List<Document> myAppDocs = new ArrayList<>(); // myAppDocs 리스트를 초기화

        for (Long docId : docIds) {
            Document document = documentRepository.findByDno(docId);
//                    .orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocs.add(document); //Document 리스트에 추가
            }
        }

        return myAppDocs; // 리스트 반환
    }


    //결재 승인
//    public void approveDocument(Long documentId) {
//        List<Approval> approvals = approvalRepository.findByDocument(documentId);
//        List<Approval> appNotRefer = approvals.stream()
//                .filter(approval -> !approval.getRefer().equals("참조"))
//                .collect(Collectors.toList());
//
//        for (int i = 0; i < appNotRefer.size(); i++) {
//            Approval approval = appNotRefer.get(i);
//
//            if (approval.getCurrent().equals("Y")) {
//                // 현재 결재자를 찾았을 경우
//                approval.setAppDate(LocalDate.now());
//                approval.setStatus(1);
//                approval.setCurrent("N");
//
//                // 다음 결재자가 있을 경우 current를 Y로 지정
//                if (i + 1 < appNotRefer.size()) {
//                    Approval nextApproval = appNotRefer.get(i + 1);
//                    nextApproval.setCurrent("Y");
//                } else {
//                    // 다음 결재자가 없는 경우, 즉 리스트의 마지막 결재자인 경우
//                    // document의 result=승인
//                    Document document = documentRepository.findByDno(documentId);
////                    Document document = documentOptional.orElse(null);
//
//                    if (document != null) {
//                        document.setResult("승인");
//                        document.setAppDate(LocalDateTime.now());
//                        documentRepository.save(document);
//                    }
//                }
//                break;
//            }
//        }
//    }
    // 결재 승인
    public void approveDocument(Long documentId) {
        List<Approval> approvals = approvalRepository.findByDocument(documentId);
        List<Approval> appNotRefer = approvals.stream()
                .filter(approval -> !approval.getRefer().equals("참조"))
                .collect(Collectors.toList());

        for (int i = 0; i < appNotRefer.size(); i++) {
            Approval approval = appNotRefer.get(i);

            if (approval.getCurrent().equals("Y")) {
                // 현재 결재자를 찾았을 경우
                approval.setAppDate(LocalDate.now());
                approval.setStatus(1);
                approval.setCurrent("N");

                // 다음 결재자가 있을 경우 current를 Y로 지정
                if (i + 1 < appNotRefer.size()) {
                    Approval nextApproval = appNotRefer.get(i + 1);
                    nextApproval.setCurrent("Y");
                } else {
                    // 다음 결재자가 없는 경우, 즉 리스트의 마지막 결재자인 경우
                    // document의 result=승인
                    Document document = documentRepository.findByDno(documentId);
                    // Document document = documentOptional.orElse(null);

                    if (document != null) {
                        document.setResult("승인");
                        document.setAppDate(LocalDateTime.now());
                        documentRepository.save(document);
                    }
                }

                // Update document's result to "진행중"
                Document document = documentRepository.findByDno(documentId);
                // Document document = documentOptional.orElse(null);

                if (document != null) {
                    document.setResult("진행중");
//                    document.setAppDate(LocalDateTime.now());
                    documentRepository.save(document);
                }

                break;
            }
        }
    }


    //결재 반려
    public void rejectDocument(ApprovalDTO approvalDTO, Long documentId) {
        List<Approval> approvals = approvalRepository.findByDocument(documentId);
        List<Approval> appNotRefer = approvals.stream()
                .filter(approval -> !approval.getRefer().equals("참조"))
                .collect(Collectors.toList());


        for (int i = 0; i < appNotRefer.size(); i++) {
            Approval approval = appNotRefer.get(i);

            if (approval.getCurrent().equals("Y")) {
                // 현재 결재자를 찾았을 경우
                if (!"참조".equals(approval.getRefer())) {
                    approval.setAppDate(LocalDate.now());
                    approval.setStatus(2);
                    approval.setCurrent("N");
                    approval.setReason(approvalDTO.getReason());

                    Document document = documentRepository.findByDno(documentId);
//                    Document document = documentOptional.orElse(null);

                    if (document != null) {
                        document.setResult("반려");
                        document.setAppDate(LocalDateTime.now());
                        documentRepository.save(document);
                    }
                    break;
                }
            }
        }
    }

    //결재 취소
    public void cancelApproval(Long id) {
        // documentId를 사용하여 해당 문서의 결재 정보를 조회합니다.
        List<Approval> approvals = approvalRepository.findByDocument(id);
        System.out.println("문서번호" + id);
        System.out.println("문서목록" + approvals);

//        두번째 결재자만 결재취소할 수 있음
//        1: 자기자신, 3: 결재 완료하면 결재 끝임
        Approval secondApprover = approvals.get(1);

        if (secondApprover != null) {

            //결재 취소 처리
            secondApprover.setAppDate(null);
            secondApprover.setStatus(0);
            approvalRepository.save(secondApprover);

            //다음 결재자 currnent N
            Approval thirdApprover = approvals.get(2);
            thirdApprover.setCurrent("N");
            approvalRepository.save(thirdApprover);

            //이전 결재자 current N 그리고 결재 취소 처리
            Approval firstApprover = approvals.get(0);
            firstApprover.setCurrent("N");
            firstApprover.setAppDate(null);
            secondApprover.setStatus(0);
        }
    }

    // 결재 회수
    public void backApproval(Long id) {
        Document document = documentService.findDocumentById(id);
        if (document != null) {
            document.setStatus(0); // 문서를 임시저장 상태로 ..
            document.setResult(null);

            Long dno = document.getDno();

            // 관련 결재정보 삭제
            List<Approval> approvals = approvalRepository.findByDocument(dno);

            for (Approval approval : approvals) {
                approvalRepository.delete(approval);
            }

            // sno 부여 dno 초기화
            Long maxSno = documentRepository.findMaxSno(); // DB에서 임시저장 번호의 최대값을 가져옴
            if (maxSno == null) {
                maxSno = 0L;
            }
            document.setSno(maxSno + 1); // 임시저장 번호 생성
            document.setDno(null); // 저장번호 null로
//            document.setResult(null);
        } else {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
        }
    }

    //내가 참조인 문서 리스트
    public List<Document> getReferencedDocuments(Long memberId) {

        List<Approval> myApps = approvalRepository.findByMemberId(memberId);
        List<Approval> myAppsRefer = myApps.stream()
                .filter(approval -> "참조".equals(approval.getRefer()))
                .collect(Collectors.toList());
        List<Long> docIds = new ArrayList<>();

        for (Approval approval : myAppsRefer) {
            Long docId = approval.getDocument(); //Approval에서 document만 찾음
            if (docId != null) {
                docIds.add(docId); //docIds 리스트에 추가
            } else {
                continue; //null이면 건너뜀
            }
        }

        List<Document> myAppDocsRefer = new ArrayList<>(); // myAppDocs 리스트를 초기화

        for (Long docId : docIds) {
            Document document = documentRepository.findByDno(docId);
//                    .orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocsRefer.add(document); //Document 리스트에 추가
            }
        }
        return myAppDocsRefer; // 리스트 반환
    }


}

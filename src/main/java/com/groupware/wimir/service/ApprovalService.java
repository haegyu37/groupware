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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private DocumentService documentService;

    //결재 요청
    public ResponseEntity<Approval> setCommonApproval(Document document, DocumentDTO documentDTO, int tempStatus) {

        //document의 id값 부여
        Long maxDocId = documentRepository.findMaxId();
        if (maxDocId == null) {
            maxDocId = 1L;
        } else {
            maxDocId = maxDocId + 1;
        }

        List<Approval> savedApprovals = new ArrayList<>(); // 변경된 부분: 저장된 approvals를 모두 저장할 리스트

        //저장된 결재라인 불러옴
        if (documentDTO.getLineId() != null) {
            List<Approval> approvals = approvalRepository.findByLineId(documentDTO.getLineId());

            for (int i = 0; i < approvals.size(); i++) {
                Approval approval = approvals.get(i);
                approval.setDocument(document);
                approval.setName(approval.getName());
                //순서값 설정
                if (i == 0) {
                    approval.setCurrent("Y");
                } else {
                    approval.setCurrent("N");
                }
                //결재라인 임시저장
                if (tempStatus == 0) {
                    approval.setTemp(0);
                } else {
                    approval.setTemp(1);
                }

                savedApprovals.add(approval); // 변경된 부분: 수정된 approval을 저장
            }
        } else { //결재자 지정
            List<Long> approvers = documentDTO.getApprovers();
            Long currentMemberId = SecurityUtil.getCurrentMemberId();

            //첫번째 결재자 상신자
            approvers.add(0, currentMemberId);

            for (int i = 0; i < approvers.size(); i++) {
                Long approverId = approvers.get(i);
                if (approverId != null) {
                    Approval approval = new Approval();
                    approval.setDocument(document);
                    approval.setMemberId(approverId);
                    approval.setRefer("결재");

                    if (approvers.size() == 4) {
                        if (i == approvers.size() - 1) {
                            approval.setRefer("참조");
                        }
                    }

                    //순서값 설정
                    if (i == 0) {
                        approval.setCurrent("Y");
                    } else {
                        approval.setCurrent("N");
                    }
                    //결재라인 임시저장
                    //결재라인 임시저장
                    if (tempStatus == 0) {
                        approval.setTemp(0);
                    } else {
                        approval.setTemp(1);
                    }

                    savedApprovals.add(approval); // 변경된 부분: 새로운 approval을 저장
                }
            }
        }

        // 변경된 부분: 모든 approvals를 저장
        savedApprovals = approvalRepository.saveAll(savedApprovals);

        // 저장된 마지막 approval을 반환
        Approval lastSavedApproval = savedApprovals.get(savedApprovals.size() - 1);
        return ResponseEntity.ok(lastSavedApproval);
    }

    // 결재 저장
    public ResponseEntity<Approval> setApproval(Document document, DocumentDTO documentDTO) {
        return setCommonApproval(document, documentDTO, 1); // 1은 결재 요청 상태를 나타냅니다.
    }

    // 결재 임시저장
    public ResponseEntity<Approval> setTempApproval(Document document, DocumentDTO documentDTO) {
        return setCommonApproval(document, documentDTO, 0); // 0은 결재 임시저장 상태를 나타냅니다.
    }

    //결재 수정 -> 저장
    public List<Approval> updateApproval(Document document, DocumentDTO documentDTO) {
//        Long id = document.getId();
        List<Approval> approvals = approvalRepository.findByDocument(document);
        return updateCommonApproval(document, approvals, documentDTO, 1);
    }

    //결재 수정 -> 임시저장
    public List<Approval> updateTempApproval(Document document, DocumentDTO documentDTO) {
//        Long id = document.getId();
        List<Approval> approvals = approvalRepository.findByDocument(document);
        return updateCommonApproval(document, approvals, documentDTO, 0);
    }

    //결재 수정
    private List<Approval> updateCommonApproval(Document document, List<Approval> approvals, DocumentDTO documentDTO, int tempStatus) {
        if (documentDTO.getLineId() != null) {
            List<Approval> line = approvalRepository.findByLineId(documentDTO.getLineId());

            if (approvals.size() > line.size()) {
                Approval referApp = approvals.get(3);
                approvalRepository.delete(referApp);
                approvals.remove(referApp);
            }

            for (int i = 0; i < line.size(); i++) {
                Approval approval = line.get(i);
                approval.setDocument(document);

                if (i == 0) {
                    approval.setCurrent("Y");
                } else {
                    approval.setCurrent("N");
                }

                //결재라인 임시저장
                if (tempStatus == 0) {
                    approval.setTemp(0);
                } else {
                    approval.setTemp(1);
                }

                approvals.set(i, approval);
            }
        } else {
            List<Long> approvers = documentDTO.getApprovers();
            Long currentMemberId = SecurityUtil.getCurrentMemberId();

            if (approvals.size() > approvers.size()) {
                Approval referApp = approvals.get(3);
                approvalRepository.delete(referApp);
                approvals.remove(referApp);
            }

            approvers.add(0, currentMemberId);

            for (int i = 0; i < approvers.size(); i++) {
                Long approverId = approvers.get(i);

                if (approverId != null) {
                    if (i < approvals.size()) { // Approval 수정
                        Approval approval = approvals.get(i);
                        approval.setDocument(document);
                        approval.setMemberId(approverId);
                        approval.setRefer("결재");

                        if (approvers.size() == 4) {
                            if (i == approvers.size() - 1) {
                                approval.setRefer("참조");
                            }
                        }

                        if (i == 0) {
                            approval.setCurrent("Y");
                        } else {
                            approval.setCurrent("N");
                        }

                        //결재라인 임시저장
                        if (tempStatus == 0) {
                            approval.setTemp(0);
                        } else {
                            approval.setTemp(1);
                        }

                    } else { // 참조 없 -> 참조 있 Approval 추가
                        Approval approval = new Approval();
                        approval.setDocument(document);
                        approval.setMemberId(approverId);
                        approval.setRefer("결재");
                        //결재라인 임시저장
                        if (tempStatus == 0) {
                            approval.setTemp(0);
                        } else {
                            approval.setTemp(1);
                        }

                        if (i == 0) {
                            approval.setCurrent("Y");
                        } else {
                            approval.setCurrent("N");
                        }

                        if (approvers.size() == 4) {
                            if (i == approvers.size() - 1) {
                                approval.setRefer("참조");
                            }
                        }

                        approvals.add(approval);
                    }
                }
            }
        }

        approvals = approvalRepository.saveAll(approvals);
        return approvals;
    }

    //내가 결재라인인 문서 리스트 all
    public List<Document> getApprovals(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Document> myAppDocs = new ArrayList<>();

        for (Approval approval : approvals) {
            if (approval.getTemp() != 0) {
                Document doc = approval.getDocument();
                myAppDocs.add(doc);
            }
        }

        return myAppDocs; // 리스트 반환
    }

    //내 결재 리스트 근데 이제 내가 결재할 차례인 .. 그것들 리스트
    public List<Document> getApprovalsNow(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id);
        List<Approval> currentApprovals = approvals.stream()
                .filter(approval -> approval != null && approval.getCurrent() != null && approval.getCurrent().equals("Y") && approval.getTemp() != 0)
                .collect(Collectors.toList());

        List<Document> myAppDocs = new ArrayList<>();
        for (Approval approval : approvals) {
            if (approval.getTemp() != 0) {
                Document doc = approval.getDocument();
                myAppDocs.add(doc);
            }
        }

        return myAppDocs; // 리스트 반환
    }

    //내가 승인한 리스트
    public List<Document> getApproved(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Document> myAppDocs = new ArrayList<>();

        for (Approval approval : approvals) {
            if (approval.getStatus() != null && !approval.getStatus().equals("반려") && approval.getAppDate() != null) {
                Document doc = approval.getDocument(); //Approval에서 document만 찾음
                if (doc != null) {
                    myAppDocs.add(doc); //docIds 리스트에 추가
                }
            }
        }

        return myAppDocs; // 리스트 반환
    }

    //내가 반려한 리스트
    public List<Document> getRejected(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Document> myAppDocs = new ArrayList<>();

        for (Approval approval : approvals) {
            if (approval.getStatus() != null && !approval.getStatus().equals("승인") && approval.getAppDate() != null) {
                Document doc = approval.getDocument(); //Approval에서 document만 찾음
                if (doc != null) {
                    myAppDocs.add(doc); //docIds 리스트에 추가
                }
            }
        }

        return myAppDocs; // 리스트 반환
    }

    //결재 승인
    public void approveDocument(Long docId, Long memberId) {
        //문서 찾아서
        Document doc = documentRepository.findById(docId).orElse(null);
//        Long documentId = doc.getId();
        List<Approval> approvals = approvalRepository.findByDocument(doc);
        List<Approval> appNotRefer = approvals.stream()
                .filter(approval -> !approval.getRefer().equals("참조"))
                .collect(Collectors.toList());

        for (int i = 0; i < appNotRefer.size(); i++) {
            Approval approval = appNotRefer.get(i);
            if (i == 0) {
                doc.setResult("진행중"); //첫번째 결재자가 결재하면 문서 상태를 진행중으로 ..
            }

            if (approval.getCurrent().equals("Y")) {
                // 현재 결재자를 찾았을 경우
                approval.setAppDate(LocalDate.now());
                approval.setStatus("승인");
                approval.setCurrent("N");

                // 다음 결재자가 있을 경우 current를 Y로 지정
                if (i != appNotRefer.size() - 1) {
                    Approval nextApproval = appNotRefer.get(i + 1);
                    nextApproval.setCurrent("Y");
                } else {
                    // 다음 결재자가 없는 경우, 즉 리스트의 마지막 결재자인 경우
                    // document의 result=승인
                    Document document = documentService.findDocumentById(docId);

                    if (document != null) {
                        document.setResult("승인");
                        document.setAppDate(LocalDate.now());
                        documentRepository.save(document);
                    }
                }
                break;
            }
        }
    }


    //결재 반려
    public void rejectDocument(ApprovalDTO approvalDTO, Long id) {
        Document doc = documentRepository.findById(id).orElse(null);
        List<Approval> approvals = approvalRepository.findByDocument(doc);
        List<Approval> appNotRefer = approvals.stream()
                .filter(approval -> !approval.getRefer().equals("참조"))
                .collect(Collectors.toList());


        for (int i = 0; i < appNotRefer.size(); i++) {
            Approval approval = appNotRefer.get(i);

            if (approval.getCurrent().equals("Y")) {
                // 현재 결재자를 찾았을 경우
                if (!"참조".equals(approval.getRefer())) {
                    approval.setAppDate(LocalDate.now());
                    approval.setStatus("반려");
                    approval.setCurrent("N");

                    Optional<Document> documentOptional = documentRepository.findById(id);

                    if (doc != null) {
                        doc.setResult("반려");
                        doc.setAppDate(LocalDate.now());
                        documentRepository.save(doc);
                    }
                    break;
                }
            }
        }
    }

    //결재 취소
    public ResponseEntity<?> cancelApproval(Long docId, Long id) {

        //id로 문서 찾아서 dno 가져오기
        Document document = documentRepository.findById(docId).orElse(null);

        //해당 문서의 결재 리스트
        List<Approval> approvals = approvalRepository.findByDocument(document);

        //해당 문서의 결재자 리스트
        List<Long> memberIds = approvals.stream()
                .map(Approval::getMemberId)
                .collect(Collectors.toList());

        int memberIndex = -1; // 초기값을 -1로 설정

        // 결재자 리스트 중에서 현재 결재자 아이디 가져오기
        for (int i = 0; i < memberIds.size(); i++) {
            if (memberIds.get(i) == id) {
                memberIndex = i;
                break;
            }
        }

        Approval nowApprover = approvals.get(memberIndex);
        Approval nextApprover = approvals.get(memberIndex + 1);

        if (nextApprover.getAppDate() == null || nextApprover.getStatus() == null) {
            if (nowApprover != null) {

                //내 결재 취소 처리
                nowApprover.setAppDate(null);
                nowApprover.setStatus(null);
                nowApprover.setCurrent("Y");
                approvalRepository.save(nowApprover);

                if (nextApprover != null) {
                    //다음 결재자 currnent N
                    nextApprover.setCurrent("N");
                    approvalRepository.save(nextApprover);
                }

            }

            if (memberIndex == 0) {
                document.setResult("결재대기");
            }
            return ResponseEntity.ok("결재가 취소되었습니다.");
        } else {
            return null;
        }
    }

    // 결재 회수
    public void backApproval(Long id) {
        Document document = documentRepository.findById(id).orElse(null);

        if (document != null) {
            document.setStatus(0); // 문서를 임시저장 상태로 ..
            document.setResult(null);

            // 관련 결재정보 삭제
            List<Approval> approvals = approvalRepository.findByDocument(document);
            for(Approval approval : approvals){
                approval.setTemp(0);
            }

            // sno 부여 dno 초기화
            Long maxSno = documentRepository.findMaxSno(); // DB에서 임시저장 번호의 최대값을 가져옴
            if (maxSno == null) {
                maxSno = 0L;
            }
            document.setSno(maxSno + 1); // 임시저장 번호 생성
            document.setDno(null); // 저장번호 null로

        } else {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
        }
    }

    //내가 참조인 문서 리스트
    public List<Document> getReferencedDocuments(Long memberId) {

        List<Approval> myApps = approvalRepository.findByMemberId(memberId);
        List<Approval> myAppsRefer = myApps.stream()
                .filter(approval -> "참조".equals(approval.getRefer()) && approval.getTemp() != 0)
                .collect(Collectors.toList());
        List<Document> docIds = new ArrayList<>();

        for (Approval approval : myAppsRefer) {
            Document docId = approval.getDocument(); //Approval에서 document만 찾음
            if (docId != null) {
                docIds.add(docId); //docIds 리스트에 추가
            } else {
                continue; //null이면 건너뜀
            }
        }

//        List<Document> myAppDocsRefer = new ArrayList<>(); // myAppDocs 리스트를 초기화
//
//        for (Document docId : docIds) {
//            Document document = documentService.findDocumentById(docId);
//            if (document != null) {
//                myAppDocsRefer.add(document); //Document 리스트에 추가
//            }
//        }
        return docIds; // 리스트 반환
    }

    //결재 삭제
    public void deleteAppByDocument(Document document) {
        List<Approval> approvals = approvalRepository.findByDocument(document);
        approvalRepository.deleteByDocument(document);
    }


}

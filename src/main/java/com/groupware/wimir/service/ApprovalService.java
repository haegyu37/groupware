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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;
import static org.apache.jena.atlas.iterator.Iter.collect;

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
    public ResponseEntity<Approval> setApproval(DocumentDTO documentDTO) {
        Approval savedApproval = null;

        Long maxDocId = documentRepository.findMaxDno(); // DB에서 문서아이디의 최대값을 가져옴 -> 중간에 문서가 삭제될 시, 잘못 번호가 매겨짐
        if (maxDocId == null) {
            maxDocId = 1L;
        } else {
            maxDocId = maxDocId + 1;
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
                approval.setDocument(maxDocId);

                if (i == 0) {
                    approval.setCurrent("Y"); // 첫 번째 결재자인 경우 current를 'Y'로 설정
//                    approval.setStatus("대기");
                } else {
                    approval.setCurrent("N"); // 그 외의 결재자는 current를 'N'으로 설정
                }

                savedApproval = approvalRepository.save(approval);
            }
            return ResponseEntity.ok(savedApproval);
        } else { //결재자 각각 지정해서 삽입

            List<Long> approvers = documentDTO.getApprovers();
            Long currentMemberId = SecurityUtil.getCurrentMemberId();
//            if (currentMemberId != null) {
            approvers.add(0, currentMemberId);
//            } else {
//                System.out.println("로그인 아이디가  null : " + currentMemberId);
//            }

            int lastIndex = documentDTO.getApprovers().size() - 1; // 배열의 맨 마지막 인덱스

            for (int i = 0; i < approvers.size(); i++) {
                Long approverId = approvers.get(i);
                Approval approval = new Approval();
                approval.setDocument(maxDocId);
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
        System.out.println("내결재" + approvals);
        List<Long> docIds = new ArrayList<>();

        for (Approval approval : approvals) {
            Long docId = approval.getDocument(); //Approval에서 document만 찾음
            if (docId != null) {
                docIds.add(docId); //docIds 리스트에 추가
            } else {
                continue; //null이면 건너뜀
            }
            System.out.println("내결재" + docId);
        }

        List<Document> myAppDocs = new ArrayList<>(); // myAppDocs 리스트를 초기화

        for (Long docId : docIds) {
            Document document = documentRepository.findByDno(docId);
//                    .orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocs.add(document); //Document 리스트에 추가
            }
        }
        System.out.println("내결재" + myAppDocs);

        return myAppDocs; // 리스트 반환
    }

    //내 결재 리스트 근데 이제 내가 결재할 차례인 .. 그것들 리스트
    public List<Document> getApprovalsNow(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id);
        List<Approval> currentApprovals = approvals.stream()
                .filter(approval -> approval != null && approval.getCurrent() != null && approval.getCurrent().equals("Y"))
                .collect(Collectors.toList());

        List<Long> docIds = new ArrayList<>();
        for (Approval approval : currentApprovals) {
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

    //내가 승인한 리스트
    public List<Document> getApproved(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Long> docIds = new ArrayList<>();

        for (Approval approval : approvals) {
            if (approval.getStatus() != null && !approval.getStatus().equals("반려") && approval.getAppDate() != null) {
                Long docId = approval.getDocument(); //Approval에서 document만 찾음
                if (docId != null) {
                    docIds.add(docId); //docIds 리스트에 추가
                }
            }
        }

        List<Document> myAppDocs = new ArrayList<>(); // myAppDocs 리스트를 초기화

        for (Long docId : docIds) {
            Document document = documentRepository.findById(docId)
                    .orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocs.add(document); //Document 리스트에 추가
            }
        }

        return myAppDocs; // 리스트 반환
    }

    //내가 반려한 리스트
    public List<Document> getRejected(Long id) {
        List<Approval> approvals = approvalRepository.findByMemberId(id); //memberId를 기준으로 Approval 리스트 찾음
        List<Long> docIds = new ArrayList<>();

        for (Approval approval : approvals) {
            if (approval.getStatus() != null && !approval.getStatus().equals("승인") && approval.getAppDate() != null) {
                Long docId = approval.getDocument(); //Approval에서 document만 찾음
                if (docId != null) {
                    docIds.add(docId); //docIds 리스트에 추가
                }
            }
        }

        List<Document> myAppDocs = new ArrayList<>(); // myAppDocs 리스트를 초기화

        for (Long docId : docIds) {
            Document document = documentRepository.findById(docId)
                    .orElse(null); //id로 Document 찾음
            if (document != null) {
                myAppDocs.add(document); //Document 리스트에 추가
            }
        }

        return myAppDocs; // 리스트 반환
    }


    //결재 승인
    public void approveDocument(Long docId, Long memberId) {
        //문서 찾아서
        Document doc = documentRepository.findById(docId).orElse(null);
        Long documentId = doc.getDno();
        List<Approval> approvals = approvalRepository.findByDocument(documentId);
        List<Approval> appNotRefer = approvals.stream()
                .filter(approval -> !approval.getRefer().equals("참조"))
                .collect(Collectors.toList());

        for (int i = 0; i < appNotRefer.size(); i++) {
            Approval approval = appNotRefer.get(i);
            if (i == 0) {
                Optional<Document> documentOptional = documentRepository.findById(documentId);
//                Document document = documentOptional.orElse(null);
                doc.setResult("진행중"); //첫번째 결재자가 결재하면 문서 상태를 진행중으로 ..
            }

            if (approval.getCurrent().equals("Y")) {
                // 현재 결재자를 찾았을 경우
                approval.setAppDate(LocalDate.now());
                approval.setStatus("승인");
                approval.setCurrent("N");

                // 다음 결재자가 있을 경우 current를 Y로 지정
                if (i + 1 < appNotRefer.size()) {
                    Approval nextApproval = appNotRefer.get(i + 1);
                    nextApproval.setCurrent("Y");
                } else {
                    // 다음 결재자가 없는 경우, 즉 리스트의 마지막 결재자인 경우
                    // document의 result=승인
                    Optional<Document> documentOptional = documentRepository.findById(documentId);
                    Document document = documentOptional.orElse(null);

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
        Long documentId = doc.getDno();
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
                    approval.setStatus("반려");
                    approval.setCurrent("N");
                    approval.setReason(approvalDTO.getReason());

                    Optional<Document> documentOptional = documentRepository.findById(documentId);
//                    Document document = documentOptional.orElse(null);

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
        Long dno = document.getDno();

        //해당 문서의 결재 리스트
        List<Approval> approvals = approvalRepository.findByDocument(dno);

        //해당 문서의 결재자 리스트
        List<Long> memberIds = approvals.stream()
                .map(Approval::getMemberId)
                .collect(Collectors.toList());

        int memberIndex = -1; // 초기값을 -1로 설정

        // 결재자 리스트 중에서 현재 결재자 아이디 가져오기
        for (int i = 0; i < memberIds.size(); i++) {
            if (memberIds.get(i) == id) {
                memberIndex = i; // 일치하는 값이 발견되면 memberIndex를 설정하고
                break; // 루프를 종료합니다.
            }
        }
        System.out.println("인덱스" + memberIndex);

        Approval nowApprover = approvals.get(memberIndex);
        Approval nextApprover = approvals.get(memberIndex + 1);
//        if (memberIndex != 0){Approval beforeApprover = approvals.get(memberIndex - 1);}

        if (nextApprover.getAppDate() == null || nextApprover.getStatus() == null) {
            if (nowApprover != null) {

                //내 결재 취소 처리
                nowApprover.setAppDate(null);
                nowApprover.setStatus(null);
                nowApprover.setCurrent("Y");
                approvalRepository.save(nowApprover);

                if(nextApprover !=null) {
                    //다음 결재자 currnent N
                    nextApprover.setCurrent("N");
                    approvalRepository.save(nextApprover);
                }

//                if (memberIndex != 0){
//                    Approval beforeApprover = approvals.get(memberIndex - 1);
//                    beforeApprover.setCurrent("Y");
//                    approvalRepository.save(beforeApprover);
//
//                }

//                if(beforeApprover != null) {
//                    //이전 결재자의 current Y
//                    beforeApprover.setCurrent("Y");
//                    approvalRepository.save(beforeApprover);
//                }

            }

            if (memberIndex == 0){
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

    //결재 삭제
    public void deleteAppByDocument(Long id) {
        List<Approval> approvals = approvalRepository.findByDocument(id);
        approvalRepository.deleteByDocument(id);
    }


}

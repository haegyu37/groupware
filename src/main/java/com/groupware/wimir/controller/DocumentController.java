package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
//import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;
//    private final ApprovalRepository approvalRepository;
//    private final ApprovalService approvalService;

    // 문서 목록(메인)
    @GetMapping(value = "/list")
    public List<Document> documentList(@PageableDefault Pageable pageable) {
        // 임시저장 상태가 아닌(1인) 문서만 조회하도록 수정
        return documentService.findDocumentListByStatusNot(0, pageable).getContent();
    }

    // 문서 조회
    @GetMapping(value = "/read/{dno}")
    public Document readDocument(@PathVariable("dno") Long dno) {
        Document document = documentService.findDocumentByDno(dno);
        if (document == null) {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + dno);
        }
        return document;
    }

    //문서작성
    @PostMapping(value = "/create")
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO) {
        Document document = new Document();
        document.setTitle(documentDTO.getTitle());
        document.setContent(documentDTO.getContent());
        document.setWriter(documentDTO.getWriter());
        document.setCreateDate(LocalDateTime.now());
        document.setStatus(documentDTO.getStatus());
        document.setDno(document.getDno()); //문서번호
        document.setSno(document.getSno()); //임시저장 번호

                if (document.getStatus() == 0) {
            // 임시저장인 경우
            Long maxSno = documentRepository.findMaxSno(); // DB에서 임시저장 번호의 최대값을 가져옴
            if (maxSno == null) {
                maxSno = 0L;
            }
            document.setSno(maxSno + 1); // 임시저장 번호 생성
        } else {
            // 작성인 경우
            Long maxDno = documentRepository.findMaxDno(); // DB에서 문서 번호의 최대값을 가져옴
            if (maxDno == null) {
                maxDno = 0L;
            }
            document.setDno(maxDno + 1); // 작성 번호 생성
        }


        // 문서를 저장하고 저장된 문서를 반환합니다.
        document = documentService.saveDocument(document);

        return ResponseEntity.ok(document);
    }

//    @PostMapping(value = "/create")
//    public ResponseEntity<Document> createDocumentWithApproval(@RequestBody DocumentDTO documentDTO, @RequestParam("approvers") List<Long> approvers) {
//        // 문서 작성
//        Document document = new Document();
//        document.setTitle(documentDTO.getTitle());
//        document.setContent(documentDTO.getContent());
//        document.setWriter(documentDTO.getWriter());
//        document.setCreateDate(LocalDateTime.now());
//        document.setStatus(documentDTO.getStatus());
//        document.setDno(document.getDno()); // 문서번호
//        document.setSno(document.getSno()); // 임시저장 번호
//
//        if (document.getStatus() == 0) {
//            // 임시저장인 경우
//            Long maxSno = documentRepository.findMaxSno(); // DB에서 임시저장 번호의 최대값을 가져옴
//            if (maxSno == null) {
//                maxSno = 0L;
//            }
//            document.setSno(maxSno + 1); // 임시저장 번호 생성
//        } else {
//            // 작성인 경우
//            Long maxDno = documentRepository.findMaxDno(); // DB에서 문서 번호의 최대값을 가져옴
//            if (maxDno == null) {
//                maxDno = 0L;
//            }
//            document.setDno(maxDno + 1); // 작성 번호 생성
//        }
//
//        // 문서를 저장하고 저장된 문서를 반환합니다.
//        document = documentService.saveDocument(document);
//
//        // 결재자들을 지정하여 Approval 객체를 생성 및 저장합니다.
//        int step = 1; // 결재 순서를 나타내는 변수
//        for (Long approversId : approvers) {
//            Approval approval = new Approval();
//            approval.setDocument(document);
//            approval.setMemberId(memberRepository.findById(approversId)
//                    .orElseThrow(() -> new ResourceNotFoundException("결재자를 찾을 수 없습니다. : " + approversId)));
//            approval.setStatus(0); // 결재 대기 상태로 설정
//            approval.setApprovalDate(null); // 결재 일시 초기화 (아직 결재가 이루어지지 않았으므로 null로 설정)
//            approval.setStep(step++); // 결재 순서 지정
//            approval.setName(approval.getName()); // 결재라인 이름 지정 (임의로 "결재라인 1", "결재라인 2"와 같이 설정)
//            approvalRepository.save(approval);
//        }
//
//        return ResponseEntity.ok(document);
//    }

    // 문서 수정
    @PutMapping(value = "/update/{dno}")
    public Document updateDocument(@PathVariable("dno") Long dno, @RequestBody DocumentDTO documentDTO) {
        Document updateDocument = documentRepository.findByDno(dno)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + dno));
        updateDocument.setTitle(documentDTO.getTitle());
        updateDocument.setContent(documentDTO.getContent());
        updateDocument.setUpdateDate(LocalDateTime.now());
        return documentRepository.save(updateDocument);
    }

    // 문서 삭제
    @DeleteMapping(value = "/delete/{dno}")
    public void deleteDocument(@PathVariable("dno") Long dno) {
        documentService.deleteDocument(dno);
    }

    // 임시저장된 문서 목록
    @GetMapping(value = "/savelist")
    public List<Document> saveDocumentList() {
        return documentService.findSaveDocumentList();
    }

    // 임시저장된 문서 조회
    @GetMapping(value = "/editread/{sno}")
    public Document editDocument(@PathVariable("sno") Long sno) {
        return documentService.findDocumentBySno(sno);
    }

    // 임시저장 문서 수정(기존 데이터를 가져오는지 의문)
    @PutMapping(value = "/edit/{sno}")
    public Document updateEditDocument(@PathVariable("sno") Long sno, @RequestBody DocumentDTO documentDTO) {
        Document updateDocument = documentRepository.findBySno(sno);
        if (updateDocument != null) {
            updateDocument.setTitle(documentDTO.getTitle());
            updateDocument.setContent(documentDTO.getContent());
            updateDocument.setCreateDate(LocalDateTime.now());

            if (documentDTO.getStatus() == 0) { // 임시저장인 경우 그냥 저장
            } else {
                // 작성인 경우
                Long maxDno = documentRepository.findMaxDno(); // DB에서 문서 번호의 최대값을 가져옴
                if (maxDno == null) {
                    maxDno = 0L;
                }
                updateDocument.setDno(maxDno + 1); // 작성 번호 생성
                updateDocument.setSno(null);
                updateDocument.setStatus(1);
            }
            documentRepository.save(updateDocument);
        }
        return updateDocument;
    }

    // 임시저장된 문서 삭제
    @DeleteMapping(value = "/editdelete/{sno}")
    public void deleteEditDocument(@PathVariable("sno") Long sno) {
        documentService.deleteEditDocument(sno);
    }



}
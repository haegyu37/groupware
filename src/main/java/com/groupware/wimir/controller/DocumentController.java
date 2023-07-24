package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.ApprovalService;
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
    private final ApprovalRepository approvalRepository;
    private final ApprovalService approvalService;

    // 문서 목록(메인)
    @GetMapping(value = "/list")
    public List<Document> documentList(@PageableDefault Pageable pageable) {
        return documentService.findDocumentList(pageable).getContent();
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

    @PostMapping(value = "/createApprovers")
    public ResponseEntity<Void> createApprovers(@RequestBody List<ApprovalDTO> approvalDTOs) {
        Document document = documentService.getMostRecentDocument(); // 이전 요청에서 저장한 문서 가져오기

        // 결재자들을 저장합니다.
        for (ApprovalDTO approvalDTO : approvalDTOs) {
            Approval approver = new Approval();
            approver.setId(approvalDTO.getApprover());
            // 나머지 코드는 이전과 동일합니다.

            // 결재자를 저장합니다.
            approvalService.saveApprover(approver);

            // 문서와 결재자의 관계를 설정합니다.
            document.getApprovals().add(approver);
        }

        // 수정된 문서를 다시 저장합니다.
        document = documentService.saveDocument(document);

        return ResponseEntity.ok().build();
    }

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
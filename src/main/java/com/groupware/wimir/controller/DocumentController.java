package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
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

    // 문서 목록
    @GetMapping(value = "/list")
    public List<Document> documentList(@PageableDefault Pageable pageable) {
        // 임시저장 상태가 아닌(1인) 문서만 조회하도록 수정
        return documentService.findDocumentListByStatusNot(0, pageable).getContent();
    }

    // 임시저장 목록
    @GetMapping(value = "/savelist")
    public List<Document> saveDocumentList() {
        return documentService.findSaveDocumentList();
    }

    // 문서 작성
    @PostMapping(value = "/create/{memberId}")
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO, @PathVariable("memberId") Long writerId) {
        Document document = new Document();
        document.setTitle(documentDTO.getTitle());
        document.setContent(documentDTO.getContent());
        document.setCreateDate(LocalDateTime.now());
        document.setStatus(documentDTO.getStatus());
        document.setDno(document.getDno()); //문서번호
        document.setSno(document.getSno()); //임시저장 번호

        System.out.println("document : " + document + "    writerId" + writerId);

        Member writer = memberRepository.findById(writerId)
                .orElseThrow(() -> new RuntimeException("해당 작성자를 찾을 수 없습니다."));
        document.setWriter(documentDTO.getWriter());

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

    // 문서 상세 조회
    @GetMapping(value = "/read/{id}")
    public Document readDocument(@PathVariable("id") Long id) {
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
        }
        return document;
    }

    // 문서 수정
    @PutMapping(value = "/update/{id}")
    public Document updateDocument(@PathVariable("id") Long id, @RequestBody DocumentDTO documentDTO) {
        Document updateDocument = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));

        if (updateDocument != null) {
            updateDocument.setTitle(documentDTO.getTitle());
            updateDocument.setContent(documentDTO.getContent());
            updateDocument.setUpdateDate(LocalDateTime.now());

            if (documentDTO.getStatus() == 0) {
                // 임시저장인 경우 그냥 저장
            } else {
                // 작성인 경우
                Long maxDno = documentRepository.findMaxDno();
                if (maxDno == null) {
                    maxDno = 0L;
                }
                if (updateDocument.getDno() == null || updateDocument.getDno() == 0) {
                    updateDocument.setDno(maxDno + 1);
                }
                updateDocument.setSno(null);
                updateDocument.setStatus(1);
            }

            return documentRepository.save(updateDocument);
        }

        throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
    }

    // 문서 삭제
    @DeleteMapping(value = "/delete/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        documentService.deleteDocument(id);
    }



}
package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    // 문서 목록(메인)
    @GetMapping(value = "/list")
    public List<Document> documentList(@PageableDefault Pageable pageable) {
        return documentService.findDocumentList(pageable).getContent();
    }

    // 상신 문서 목록
    @GetMapping(value = "/list/{writer}")
    public List<Document> submitList(@PathVariable Member writer, @PageableDefault Pageable pageable) {
        return documentService.findDocumentListByWriter(writer, pageable).getContent();
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

    // 문서 작성
    @PostMapping(value = "/create/{writer-id}")
    public Document createDocument(@RequestBody Document document, @PathVariable("writer-id") Long writerId) {
        document.setCreateDate(LocalDateTime.now());
        System.out.println("document : " + document + "    writer-id" + writerId);

        Member writer = memberRepository.findById(writerId)
                .orElseThrow(() -> new RuntimeException("해당 작성자를 찾을 수 없습니다."));
        document.setWriter(writer);

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
        documentRepository.save(document);
        return document;
    }

    //  문서 수정
    @PutMapping(value = "/update/{dno}")
    public Document updateDocument(@PathVariable("dno") Long dno, @RequestBody Document document) {
        Document updateDocument = documentRepository.findByDno(dno)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + dno));
        updateDocument.setTitle(document.getTitle());
        updateDocument.setContent(document.getContent());
        updateDocument.setUpdateDate(LocalDateTime.now());
        return documentRepository.save(updateDocument);
    }

    // 문서 삭제
    @DeleteMapping("/delete/{dno}")
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
    public Document updateEditDocument(@PathVariable("sno") Long sno, @RequestBody Document document) {
        Document updateDocument = documentRepository.findBySno(sno);
        if (updateDocument != null) {
            updateDocument.setTitle(document.getTitle());
            updateDocument.setContent(document.getContent());
            updateDocument.setCreateDate(LocalDateTime.now());

            if (document.getStatus() == 0) {    // 임시저장인 경우 그냥 저장
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


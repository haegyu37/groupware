package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;

@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private DocumentRepository documentRepository;

    // 문서 목록(메인)
    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("documentList", documentService.findDocumentList(pageable));
        return "/document/list";
    }

    // 문서 조회
    @GetMapping({"", "/"})
    public String Document(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "/document/form";
    }

    // 문서 작성
    @PostMapping
    public ResponseEntity<?> postDocument(@RequestBody Document document) {
        //결재라인 지정 (규)
        Approval approval = new Approval();
        approval.setDocument(document); // 승인 대상 문서 설정
        approval.setApprover(approval.getApprover()); // 승인자 정보 설정  -> 여기 이상함 고쳐라 해규야~^^ 
        approval.setStatus(0); // 대기 상태로 설정
        approval.setApprovalDate(null); // 승인 날짜 초기화
        approval.setStep(Position.사원); // 결재순서(직급) 설정

        document.setCreateDate(LocalDateTime.now());
        documentRepository.save(document);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 문서 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> putDocument(@PathVariable("id") Long id, @RequestBody Document document) {
        Document updateDocument = documentRepository.getOne(id);
        updateDocument.setTitle(document.getTitle());
        updateDocument.setContent(document.getContent());
        updateDocument.setUpdateDate(LocalDateTime.now());
        documentRepository.save(updateDocument);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    // 문서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable("id") Long id) {
        documentRepository.deleteById(id);
        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}

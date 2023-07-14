package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Document;
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
    public String readDocument(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "/document/form";
    }

    // 문서 작성
    @PostMapping
    public ResponseEntity<?> createDocument(@RequestBody Document document) {
        document.setCreateDate(LocalDateTime.now());
        documentRepository.save(document);
        return new ResponseEntity<>("{}", HttpStatus.CREATED);
    }

    // 문서 수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable("id") Long id, @RequestBody Document document) {
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

package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.AppService;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    private final DocumentService documentService;
    private final AppService appService;
    private final LineService lineService;

    @Autowired
    public DocumentController(DocumentService documentService, AppService appService, LineService lineService) {
        this.documentService = documentService;
        this.appService = appService;
        this.lineService = lineService;
    }

    // 문서 작성
    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
        Document savedDocument = documentService.savedDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    // 문서 수정
    @PutMapping("/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document updatedDocument) {
        Document document = documentService.getDocumentById(id);

        document.setTitle(updatedDocument.getTitle());
        document.setContent(updatedDocument.getContent());
        document.setMemberId(updatedDocument.getMemberId());
        document.setTem(updatedDocument.getTem());

        Document savedDocument = documentService.savedDocument(document);
        return ResponseEntity.ok(savedDocument);
    }

    // 모든 문서 조회
    @GetMapping("/all")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    // 문서 조회
    @GetMapping("/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    // 문서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }


}

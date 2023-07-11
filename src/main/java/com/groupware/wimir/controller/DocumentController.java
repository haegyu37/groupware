package com.groupware.wimir.controller;

import com.groupware.wimir.dto.DocumentDTO;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.AppRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.DocumentService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;
    private final MemberRepository memberRepository;
    private final AppRepository appRepository;
    private final TemplateRepository templateRepository;


    public DocumentController(DocumentService documentService, MemberRepository memberRepository, AppRepository appRepository, TemplateRepository templateRepository) {
        this.documentService = documentService;
        this.memberRepository = memberRepository;
        this.appRepository = appRepository;
        this.templateRepository = templateRepository;
    }


//    // 문서 작성
//    @PostMapping("/documents/create")
//    public ResponseEntity<Document> createDocument(@RequestBody Document document) {
//
//        Document newDocument = new Document();
//        newDocument.setTitle(document.getTitle());
//        newDocument.setContent(document.getContent());
//        newDocument.setWrittenDate(document.getWrittenDate());
//        newDocument.setMember(document.getMember());
//        newDocument.setTem(document.getTem());
//        newDocument.setApp(document.getApp());
//
//        Document createDocument = documentService.createDocument(newDocument);
//        return ResponseEntity.ok(createDocument);
//    }

    @PostMapping("/documents/create")
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO) {
        Document newDocument = new Document();
        newDocument.setTitle(documentDTO.getTitle());
        newDocument.setContent(documentDTO.getContent());
        newDocument.setWrittenDate(documentDTO.getWrittenDate());

        // memberId에 해당하는 Member 객체를 가져옵니다.
        Member member = memberRepository.findById(documentDTO.getMemberId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "직원을 찾을 수 없습니다."));
        newDocument.setMember(member);

        // appId에 해당하는 App 객체를 가져옵니다.
        App app = appRepository.findById(documentDTO.getAppId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "결재를 찾을 수 없습니다."));
        newDocument.setApp(app);

        // temId에 해당하는 Template 객체를 가져옵니다.
        Template tem = templateRepository.findById(documentDTO.getTemId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서 양식을 찾을 수 없습니다."));
        newDocument.setTem(tem);

        Document savedDocument = documentService.createDocument(newDocument);
        return ResponseEntity.ok(savedDocument);
    }

    // 문서 수정
    @PutMapping("/documents/update/{id}")
    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document updatedDocument) {
        Document document = documentService.getDocumentById(id);

        document.setTitle(updatedDocument.getTitle());
        document.setContent(updatedDocument.getContent());
        document.setApp(updatedDocument.getApp());

        Document savedDocument = documentService.updateDocument(document); // 수정된 부분
        return ResponseEntity.ok(savedDocument);
    }

    // 문서 리스트 조회
    @GetMapping("/documents/list")
    public ResponseEntity<List<Document>> getAllDocuments() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);
    }

    // 문서 조회
    @GetMapping("/documents/view/{id}")
    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
        Document document = documentService.getDocumentById(id);
        return ResponseEntity.ok(document);
    }

    // 문서 삭제
    @DeleteMapping("/documents/delete/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        documentService.deleteDocument(id);
        return ResponseEntity.noContent().build();
    }
}
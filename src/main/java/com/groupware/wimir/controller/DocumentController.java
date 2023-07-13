//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.dto.DocumentDTO;
//import com.groupware.wimir.entity.App;
//import com.groupware.wimir.entity.Document;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.entity.Template;
//import com.groupware.wimir.repository.AppRepository;
//import com.groupware.wimir.repository.DocumentRepository;
//import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.repository.TemplateRepository;
//import com.groupware.wimir.service.DocumentService;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/document")
//public class DocumentController {
//
//    private final DocumentService documentService;
//    private final MemberRepository memberRepository;
//    private final AppRepository appRepository;
//    private final TemplateRepository templateRepository;
//    private final DocumentRepository documentRepository;
//
//
//    public DocumentController(DocumentService documentService, MemberRepository memberRepository, AppRepository appRepository, TemplateRepository templateRepository, DocumentRepository documentRepository) {
//        this.documentService = documentService;
//        this.memberRepository = memberRepository;
//        this.appRepository = appRepository;
//        this.templateRepository = templateRepository;
//        this.documentRepository = documentRepository;
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO) {
//        Document newDocument = new Document();
//        newDocument.setTitle(documentDTO.getTitle());
//        newDocument.setContent(documentDTO.getContent());
//        newDocument.setWrittenDate(documentDTO.getWrittenDate());
//
//        // memberId에 해당하는 Member 객체를 가져옵니다.
//        Member member = memberRepository.findById(documentDTO.getMemberId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "직원을 찾을 수 없습니다."));
////        newDocument.setMember(member);
//
//        // appId에 해당하는 App 객체를 가져옵니다.
//        App app = appRepository.findById(documentDTO.getAppId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "결재를 찾을 수 없습니다."));
////        newDocument.setApp(app);
//
//        // temId에 해당하는 Template 객체를 가져옵니다.
//        Template template = templateRepository.findById(documentDTO.getTemId())
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서 양식을 찾을 수 없습니다."));
////        newDocument.setTemplate(template);
//
//        Document savedDocument = documentService.createDocument(newDocument);
//        return ResponseEntity.ok(savedDocument);
////        return "/documents/viewForm";
//    }
//
//    // 문서 수정
////    @PutMapping("/update/{id}")
////    public ResponseEntity<Document> updateDocument(@PathVariable Long id, @RequestBody Document updatedDocument) {
////        Document document = documentService.getDocumentById(id);
////
////        document.setTitle(updatedDocument.getTitle());
////        document.setContent(updatedDocument.getContent());
////        document.setApp(updatedDocument.getApp());
////
////        Document savedDocument = documentService.updateDocument(document); // 수정된 부분
////        return ResponseEntity.ok(savedDocument);
//////        return "/documents/viewForm";
////    }
//
//    // 문서 리스트 조회
//    @GetMapping("/list")
//    public ResponseEntity<List<Document>> getAllDocuments() {
//        List<Document> documents = documentService.getAllDocuments();
//        return ResponseEntity.ok(documents);
////        return "/documents/listForm";
//    }
//
//    // 문서 조회
//    @GetMapping("/view/{id}")
//    public ResponseEntity<Document> getDocumentById(@PathVariable Long id) {
//        Document document = documentService.getDocumentById(id);
//        return ResponseEntity.ok(document);
////        return "/documents/viewForm";
//    }
//
//    // 문서 삭제
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
//        documentService.deleteDocument(id);
//        return ResponseEntity.noContent().build();
////        return "/documents/listForm";
//    }
//
////    //결재 생성
////    @PostMapping("/{documentId}/app")
////    public ResponseEntity<App> createApp(@PathVariable("documentId") Long documentId, @RequestBody App app) {
////        Document document = documentService.getDocumentById(documentId);
////        if (document != null) {
////            app.setDoc(document);
////            App createdApp = appService.createApp(app);
////            return new ResponseEntity<>(createdApp, HttpStatus.CREATED);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
////
////    //결재라인 생성
////    @PostMapping("/{documentId}/line")
////    public ResponseEntity<Line> createLine(@PathVariable("documentId") Long documentId, @RequestBody Line line) {
////        Document document = documentService.getDocumentById(documentId);
////        if (document != null) {
////            line.setDocument(document);
////            Line createdLine = lineService.createLine(line);
////            return new ResponseEntity<>(createdLine, HttpStatus.CREATED);
////        } else {
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
////        }
////    }
//}
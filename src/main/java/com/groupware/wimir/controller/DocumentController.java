package com.groupware.wimir.controller;

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

    // 문서 목록(메인)
<<<<<<< HEAD
    @GetMapping
    public String mainDocument(@PageableDefault Pageable pageable, Model model) {
        model.addAttribute("documentList", documentService.findDocumentList(pageable));
        return "/document/list";
    }

    // 문서 조회
    @GetMapping("/read")
    public String readDocument(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "/document/read";
    }

    // 문서 작성(작성 후, 작성글 조회)
    @PostMapping("/write")
    public String createDocument(@RequestBody Document document) {
        document.setCreateDate(LocalDateTime.now());
        Document savedDocument = documentRepository.save(document);
        return "redirect:/document/read?id=" + savedDocument.getId();
    }

//    // 문서 작성
//    @PostMapping
//    public ResponseEntity<?> createDocument(@RequestBody Document document) {
//        document.setCreateDate(LocalDateTime.now());
//        documentRepository.save(document);
//        return new ResponseEntity<>("{}", HttpStatus.CREATED);
//    }

//    // 문서 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateDocument(@PathVariable("id") Long id, @RequestBody Document document) {
//        Document updateDocument = documentRepository.getOne(id);
//        updateDocument.setTitle(document.getTitle());
//        updateDocument.setContent(document.getContent());
//        updateDocument.setUpdateDate(LocalDateTime.now());
//        documentRepository.save(updateDocument);
//
//        return new ResponseEntity<>("{}", HttpStatus.OK);
//    }

    // 문서 수정(조회 페이지에서 수정)
    @GetMapping("/read/update")
    public String updateDocumentPage(@RequestParam(value = "id", defaultValue = "0") Long id, Model model) {
        model.addAttribute("document", documentService.findDocumentById(id));
        return "/document/update";
    }

    @PostMapping("/read/update")
    public String updateDocument(@RequestParam(value = "id", defaultValue = "0") Long id, @RequestBody Document document) {
        Document updateDocument = documentRepository.getOne(id);
        updateDocument.setTitle(document.getTitle());
        updateDocument.setContent(document.getContent());
        updateDocument.setUpdateDate(LocalDateTime.now());
        documentRepository.save(updateDocument);
        return "redirect:/document/read?id=" + updateDocument.getId();
=======
    @GetMapping(value = "/list")
    public List<Document> documentList(@PageableDefault Pageable pageable) {
        return documentService.findDocumentList(pageable).getContent();
    }

    // 문서 조회
    @GetMapping(value = "/read/{id}")
    public Document readDocument(@PathVariable("id") Long id) {
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
        }
        return document;
    }

    //  문서 작성 status에 따라 작성과 임시저장으로 바뀜. 현재 saveid와 상관없이 모두 작성됨, saveId는 auto 안됨
    @PostMapping(value = "/create")
    public Document createDocument(@RequestBody Document document) {
        if (document.getSaveId() != null && document.getSaveId() != 0) {
            // 임시저장인 경우 DB에 저장
            document.setId(null);
            document.setCreateDate(LocalDateTime.now());
        } else {
            // 작성인 경우 문서 등록
            document.setSaveId(null);
            document.setCreateDate(LocalDateTime.now());
        }
        return documentRepository.save(document);
    }

    //  문서 수정
    @PutMapping(value = "/update/{id}")
    public Document updateDocument(@PathVariable("id") Long id, @RequestBody Document document) {
        Document updateDocument = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));
        updateDocument.setTitle(document.getTitle());
        updateDocument.setContent(document.getContent());
        updateDocument.setUpdateDate(LocalDateTime.now());
        return documentRepository.save(updateDocument);
>>>>>>> main
    }

    // 문서 삭제
    @DeleteMapping(value = "/delete/{id}")
    public void deleteDocument(@PathVariable("id") Long id) {
        documentRepository.deleteById(id);
    }

    // 임시저장된 문서 목록 saveId만이 아니라 모든 문서 불러옴
    @GetMapping(value = "/savelist")
    public List<Document> saveDocumentList() {
        return documentService.findSaveDocumentList();
    }

    // 임시저장된 문서 불러오기
    @GetMapping(value = "/edit/{saveId}")
    public Document editDocument(@PathVariable("saveId") Long saveId) {
        return documentService.findDocumentBySaveId(saveId);
    }

    // 임시저장된 문서 재작성 status와 saveId 변경안됨.
    @PostMapping(value = "/edit/{saveId}")
    public Document updateEditDocument(@PathVariable("saveId") Long saveId, @RequestBody Document document) {
        Document updateDocument = documentRepository.findBySaveId(saveId);
        if (updateDocument == null) {
            throw new ResourceNotFoundException("임시저장된 문서를 찾을 수 없습니다. : " + saveId);
        }
        updateDocument.setTitle(document.getTitle());
        updateDocument.setContent(document.getContent());
        updateDocument.setCreateDate(LocalDateTime.now());
        return documentRepository.save(updateDocument);
    }

//    // 임시저장 문서 삭제
//    @DeleteMapping(value = "/edit/{saveId}")
//    public Document deleteEditDocument(@PathVariable("saveId") Long saveId) {
//        documentRepository.deleteBySaveId(saveId);
//    }

}


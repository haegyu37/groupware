package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final DocumentService documentService;

    // 문서 작성 및 결재자 지정
    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody Document document) {
        // 문서 작성 정보 설정
        document.setCreateDate(LocalDateTime.now());

//        // 결재자 정보 설정
//        List<Member> approverlist = memberRepository.findAllById(approvers);
//        if (approvers.isEmpty()) {
//            return new ResponseEntity<>("결재자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
//        }
//        document.setApprovers(approverlist);

        Document savedDocument = documentRepository.save(document);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }


//    @PostMapping("/create")
//    public ResponseEntity<String> createDocument(@RequestBody Document document) {
//        // 요청으로부터 DocumentDTO 객체를 받아와서 처리합니다.
//        // DocumentDTO는 작성할 문서에 대한 정보와 결재자 및 참조자 정보를 포함합니다.
//
//        // 문서 정보를 생성합니다.
//        document.setCreateDate(LocalDateTime.now());
//
//        // 작성자 정보를 설정합니다.
//        Member writer = memberService.getMemberById(document.getWriter().getId());
//        document.setWriter(writer);
//
//        // 결재자 정보를 설정합니다.
//        List<Long> approvers = new ArrayList<>();
//        for (Long approverId : document.getApprovers()) {
//            Member approver = memberService.getMemberById(approverId);
//            approvers.add(approver.getId());
//        }
//        document.setApprovers(approvers);
//
//        // 참조자 정보를 설정합니다.
//        List<Long> viewers = new ArrayList<>();
//        for (Long viewerId : document.getViewers()) {
//            Member viewer = memberService.getMemberById(viewerId);
//            viewers.add(viewer.getId());
//        }
//        document.setViewers(viewers);
//
//        // 문서 생성 및 저장 처리
//        documentService.saveDocument(document);
//
//        return ResponseEntity.ok("Document created successfully.");
//    }


    // 문서 조회
    @GetMapping(value = "/read/{id}")
    public ResponseEntity<?> readDocument(@PathVariable("id") Long id) {
        Document document = documentRepository.findById(id)
                .orElse(null);
        if (document == null) {
            return new ResponseEntity<>("문서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(document, HttpStatus.OK);
    }

    // 문서 수정
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<?> updateDocument(@PathVariable("id") Long id, @RequestBody Document updatedDocument) {
        Document document = documentRepository.findById(id)
                .orElse(null);
        if (document == null) {
            return new ResponseEntity<>("문서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        // 업데이트할 내용 설정
        document.setTitle(updatedDocument.getTitle());
        document.setContent(updatedDocument.getContent());

        Document savedDocument = documentRepository.save(document);
        return new ResponseEntity<>(savedDocument, HttpStatus.OK);
    }

    // 문서 삭제
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<?> deleteDocument(@PathVariable("id") Long id) {
        Document document = documentRepository.findById(id)
                .orElse(null);
        if (document == null) {
            return new ResponseEntity<>("문서를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        }

        documentRepository.deleteById(id);
        return new ResponseEntity<>("문서가 삭제되었습니다.", HttpStatus.OK);
    }
}

package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;

    // 문서 작성 및 결재자 지정
    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody Document document, @RequestParam List<Long> approverIds) {
        // 문서 작성 정보 설정
        document.setCreateDate(LocalDateTime.now());

        // 결재자 정보 설정
        List<Member> approvers = memberRepository.findAllById(approverIds);
        if (approvers.isEmpty()) {
            return new ResponseEntity<>("결재자를 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        document.setApprovers(approvers);

        Document savedDocument = documentRepository.save(document);
        return new ResponseEntity<>(savedDocument, HttpStatus.CREATED);
    }

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

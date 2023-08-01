package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.exception.ResourceNotFoundException;
//import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.ApprovalService;
import com.groupware.wimir.service.DocumentService;
import com.groupware.wimir.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final ApprovalService approvalService;
    private final TemplateRepository templateRepository;

    // 문서 목록(정상 저장 전체 다 보도록)
    @GetMapping(value = "/list")
    public List<Document> documentList(@PageableDefault Pageable pageable) {
        // 임시저장 상태가 아닌(1인) 문서만 조회하도록 수정
        return documentService.findDocumentListByStatusNot(0, pageable).getContent();
    }

    //내가 작성한 임시저장 리스트
    @GetMapping(value = "/savelist")
    public Page<Document> getMySaveList(@PageableDefault Pageable pageable) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatus(currentMemberId, 0, pageable);
    }

    //내가 작성한 저장 리스트
    @GetMapping(value = "/mylist")
    public Page<Document> getMyList(@PageableDefault Pageable pageable) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatus(currentMemberId, 1, pageable);
    }

    // 카테고리별 작성된 문서 리스트(fun11번에 이용할듯)-승인, 반려 기능 추가되면
    @GetMapping(value ="/categorylist/{id}")
    public Page<Document> getDocumentsByTemplateList(@PageableDefault Pageable pageable, @PathVariable Long id, @RequestParam(required = false) Integer status) {
        return documentService.findDocumentListByTemplateIdAndStatus(id, 1, pageable);
    }

    // 카테고리별 자신이 작성한 문서 리스트(fun8번 결재 상태 추가되어야 함)
    @GetMapping(value = "/categorymylist/{id}")
    public Page<Document> getDocumentsByMyTemplateList(@PageableDefault Pageable pageable, @PathVariable Long id, @RequestParam(required = false) Integer status) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndTemplateIdAndStatus(currentMemberId, id, 1, pageable);
    }

    // 문서 작성
    @PostMapping(value = "/create")
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO) {
        Document document = new Document();

        document.setTitle(documentDTO.getTitle());
        document.setContent(documentDTO.getContent());
        documentService.setWriterByToken(document);
        document.setCreateDate(LocalDateTime.now());
        document.setStatus(documentDTO.getStatus());
//        document.setDno(document.getDno()); //문서번호
//        document.setSno(document.getSno()); //임시저장 번호
        document.setTemplate(documentDTO.getTemplate());    // 양식명
//        System.out.println(documentDTO.getTemplate());
        document.setResult(0);
        approvalService.setApproval(documentDTO);


//        int result = approvalService.submitApproval(documentDTO);

        //임시저장 관련
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

        // 문서를 저장하고 저장된 문서를 반환
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

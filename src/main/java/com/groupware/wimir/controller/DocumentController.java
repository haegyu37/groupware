package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.DocumentDTO;
import com.groupware.wimir.DTO.DocumentResponseDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private final LineService lineService;
    private final ApprovalRepository approvalRepository;

    // 문서 목록(정상 저장 전체 다 보도록)
    @GetMapping(value = "/list")
    public List<Document> documentList() {
        // 임시저장 상태가 아닌(1인) 문서만 조회하도록 수정
        return documentService.findDocumentListByStatusNot(0);
    }

    //내가 작성한 임시저장 리스트
    @GetMapping(value = "/savelist")
    public List<Document> getMySaveList() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatus(currentMemberId, 0);
    }

    //내가 작성한 저장 리스트
    @GetMapping(value = "/mylist")
    public List<Document> getMyList() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatus(currentMemberId, 1);
    }

    //내가 작성한 저장 리스트 결재대기
    @GetMapping(value = "/mylist/waiting")
    public List<Document> getMyListWaiting() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatusAndResult(currentMemberId, 1, "결재대기");
    }

    //내가 작성한 저장 리스트 진행중
    @GetMapping(value = "/mylist/ing")
    public List<Document> getMyListApproving() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatusAndResult(currentMemberId, 1, "진행중");
    }

    //내가 작성한 저장 리스트 승인
    @GetMapping(value = "/mylist/approved")
    public List<Document> getMyListApproved() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatusAndResult(currentMemberId, 1, "승인");
    }

    //내가 작성한 저장 리스트 반려
    @GetMapping(value = "/mylist/rejected")
    public List<Document> getMyListRejected() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatusAndResult(currentMemberId, 1, "반려");
    }

    // 카테고리별 작성된 문서 리스트(fun11번에 이용할듯)-승인, 반려 기능 추가되면
    @GetMapping(value = "/categorylist/{id}")
    public List<Document> getDocumentsByTemplateList(@PathVariable Long id, @RequestParam(required = false) Integer status) {
        return documentService.findDocumentListByTemplateIdAndStatus(id, 1);
    }

    // 카테고리별 자신이 작성한 문서 리스트(fun8번 결재 상태 추가되어야 함)
    @GetMapping(value = "/categorymylist/{id}")
    public List<Document> getDocumentsByMyTemplateList(@PathVariable Long id, @RequestParam(required = false) Integer status) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndTemplateIdAndStatus(currentMemberId, id, 1);
    }

    // 문서 작성
    @PostMapping(value = "/create")
    public ResponseEntity<Document> createDocument(@RequestBody DocumentDTO documentDTO) {
        Document document = new Document();

        document.setTitle(documentDTO.getTitle());
        document.setContent(documentDTO.getContent());
        documentService.setWriterByToken(document);
        document.setCreateDate(LocalDate.now());
        document.setStatus(documentDTO.getStatus());
        Template template = templateRepository.findById(documentDTO.getTemplate()).orElse(null);
        document.setTemplate(template);    // 양식명

        //임시저장 관련
        if (document.getStatus() == 0) {
            // 임시저장인 경우
            if (documentDTO.getApprovers() != null || documentDTO.getLineId() != null) {
                approvalService.setTempApproval(documentDTO); //결재요청
            }
            Long maxSno = documentRepository.findMaxSno(); // DB에서 임시저장 번호의 최대값을 가져옴
            if (maxSno == null) {
                maxSno = 0L;
            }
            document.setSno(maxSno + 1); // 임시저장 번호 생성
        } else {
            // 작성인 경우
            approvalService.setApproval(documentDTO); //결재요청
            document.setResult("결재대기");
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

    //문서 조회
    @GetMapping(value = "/{id}")
    public DocumentResponseDTO readDocument(@PathVariable("id") Long id) {
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
        }

        Long docId = document.getId();
        if (docId != null) {
            List<Approval> approvals = lineService.getByDocument(docId);
            Map<Long, List<Map<String, Object>>> groupedApprovals = lineService.getGroupedApprovalsDoc(approvals);

            Long currentId = SecurityUtil.getCurrentMemberId();
            Map<String, Object> appInfoForCancel = lineService.appInfoForCancel(approvals, currentId);

            return new DocumentResponseDTO(document, groupedApprovals, appInfoForCancel);
        } else {
            return new DocumentResponseDTO(document, null, null);
        }

    }

    // 문서 수정
    @PutMapping(value = "/update/{id}")
    public Document updateDocument(@PathVariable("id") Long id, @RequestBody DocumentDTO documentDTO) {
        Document updateDocument = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));

        // result가 "승인"이거나 "반려"인 경우 수정을 제한
        String result = updateDocument.getResult();
        if ("진행중".equals(result) || "승인".equals(result) || "반려".equals(result)) {
            throw new UnsupportedOperationException("결재 중인 문서는 수정할 수 없습니다.");
        }

        updateDocument.setTitle(documentDTO.getTitle());
        updateDocument.setContent(documentDTO.getContent());
        updateDocument.setUpdateDate(LocalDate.now());
        documentService.setWriterByToken(updateDocument);
        updateDocument.setStatus(documentDTO.getStatus());
        updateDocument.setResult("결재대기");


        if (documentDTO.getStatus() == 0) {
            approvalService.updateTempApproval(updateDocument, documentDTO);
            // status가 0인 경우 임시저장이므로 그냥 저장
            if (documentDTO.getLineId() != null || documentDTO.getApprovers() != null) {
                approvalService.updateTempApproval(updateDocument, documentDTO);
            }
        } else {
            approvalService.updateApproval(updateDocument, documentDTO);

            // status가 1인 경우 작성인 경우
            Long maxDno = documentRepository.findMaxDno();
            if (maxDno == null) {
                maxDno = 0L;
            }
            if (updateDocument.getDno() == null || updateDocument.getDno() == 0) {
                updateDocument.setDno(maxDno + 1);

            }
            Template template = updateDocument.getTemplate();
            if (template != null) {
                Long maxTempNo = documentRepository.findMaxTempNoByTemplate(template);
                updateDocument.setTempNo(maxTempNo + 1);
            }
            updateDocument.setSno(null);
            updateDocument.setStatus(1);

        }
        return documentRepository.save(updateDocument);


    }


    // 문서 삭제
    @DeleteMapping("/delete/{id}")
    public void deleteDocument(@PathVariable Long id) {

        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));

        // result가 "승인"이거나 "반려"인 경우 수정을 제한
        String result = document.getResult();
        if ("진행중".equals(result) || "승인".equals(result) || "반려".equals(result)) {
            throw new UnsupportedOperationException("결재 중인 문서는 수정할 수 없습니다.");
        }

        //문서 해당 결재 삭제
//        Long dno = document.getDno();
        approvalService.deleteAppByDocument(id);
        documentService.deleteDocument(id);

    }


}

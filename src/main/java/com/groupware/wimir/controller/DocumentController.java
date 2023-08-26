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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
    private final AttachmentService attachmentService;
    private final ApprovalRepository approvalRepository;

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


    //내가 작성한 저장 리스트 승인
    @GetMapping(value = "/mylist/approved")
    public Page<Document> getMyListApproved(@PageableDefault Pageable pageable) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatusAndResult(currentMemberId, 1, "승인", pageable);
    }

    //내가 작성한 저장 리스트 반려
    @GetMapping(value = "/mylist/rejected")
    public Page<Document> getMyListRejected(@PageableDefault Pageable pageable) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        return documentService.findDocumentListByWriterAndStatusAndResult(currentMemberId, 1, "반려", pageable);
    }

    //결재 완료된 문서 목록 all -> 관리자
//    @GetMapping("/listdone")
//    public List<Document> approvedDocs() {
//        List<Document> approvedDocs = documentService.getApprovedDocuments();
//        return approvedDocs;
//    }

    // 카테고리별 작성된 문서 리스트(fun11번에 이용할듯)-승인, 반려 기능 추가되면
    @GetMapping(value = "/categorylist/{id}")
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
        document.setCreateDate(LocalDate.now());
        document.setStatus(documentDTO.getStatus());

        // 템플릿의 활성화 상태 확인(비활성화된 템플릿도 보인다면 적용)
//        Template template = templateRepository.findById(documentDTO.getTemplate().getId()).orElse(null);
//        if (template == null || !template.isActive()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // 비활성화된 템플릿 선택 시 에러 응답
//        }

        document.setTemplate(documentDTO.getTemplate());    // 양식명
<<<<<<< HEAD

        document.setResult("진행중");
=======
//        System.out.println(documentDTO.getTemplate());
//        document.setResult("결재대기");
>>>>>>> main
//        approvalService.setApproval(documentDTO);

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


<<<<<<< HEAD
    // 문서 상세 조회
    @GetMapping(value = "/read/{id}")
    public Document readDocument(@PathVariable("id") Long id) {
=======
    @GetMapping(value = "/{id}")
    public DocumentResponseDTO readDocument(@PathVariable("id") Long id) {
>>>>>>> main
        Document document = documentService.findDocumentById(id);
        if (document == null) {
            throw new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id);
        }
<<<<<<< HEAD
        return document;
=======

        Long dno = document.getDno();
        List<Approval> approvals = lineService.getByDocument(dno);
        Map<Long, List<Map<String, Object>>> groupedApprovals = lineService.getGroupedApprovalsDoc(approvals);

        return new DocumentResponseDTO(document, groupedApprovals);
>>>>>>> main
    }

    //문서 조회 - 임시저장
    @GetMapping(value = "/save/{id}")
    public Optional<Document> readSaveDocument(@PathVariable("id") Long id) {
        Optional<Document> document = documentRepository.findById(id);
//        Long sno = document.getSno();
//        List<Approval> approvals = lineService.getBySno(sno); // 이러면 안될듯 ..
//        Map<Long, List<Map<String, Object>>> groupedApprovals = lineService.getGroupedApprovalsDoc(approvals);
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
//            approvalService.updateApproval(documentDTO, id);


            if (documentDTO.getStatus() == 0) {
                // status가 0인 경우 임시저장이므로 그냥 저장
            } else {
                approvalService.setApproval(documentDTO);

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
        public void deleteDocument (@PathVariable Long id){

            Document document = documentRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));

            // result가 "승인"이거나 "반려"인 경우 수정을 제한
            String result = document.getResult();
            if ("진행중".equals(result) || "승인".equals(result) || "반려".equals(result)) {
                throw new UnsupportedOperationException("결재 중인 문서는 수정할 수 없습니다.");
            }

            //문서에 해당 첨부파일 삭제
            attachmentService.deleteAttachmentByDoc(id);
            //문서 해당 결재 삭제
            Long dno = document.getDno();
            approvalService.deleteAppByDocument(dno);
            documentService.deleteDocument(id);

        }


    }

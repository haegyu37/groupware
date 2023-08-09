package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private TemplateRepository templateRepository;

    @Override
    public Document findDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElse(null);
    }

//    @Override
//    public Page<Document> findDocumentListByWriter(Member writer, Pageable pageable) {
////        Page<Document> Documents = documentRepository.findByWriter(writer, pageable);
//        return documentRepository.findByWriter(writer, pageable);
//    }

//    @Override
//    public Document findDocumentByDno(Long dno) {
//        return documentRepository.findByDno(dno).orElse(new Document());
//    }
//
//    public void deleteDocument(Long dno) {
//        Document document = documentRepository.findByDno(dno)
//                .orElseThrow(() -> new RuntimeException("해당 문서를 찾을 수 없습니다."));
//        // 문서에 속한 첨부파일들을 삭제
//        List<Attachment> attachments = attachmentService.getAttachmentsByDocumentId(document.getId());
//        for (Attachment attachment : attachments) {
//            attachmentService.deleteAttachment(attachment.getId());
//        }
//        // 문서 삭제
//        documentRepository.deleteByDno(dno);
//    }

//    @Override
//    public List<Document> findSaveDocumentList() {
//        return documentRepository.findByStatus(0); // 임시저장 상태인 문서 조회
//    }

    @Override
    public Document saveDocument(Document document) {
        if (document.getStatus() == 1) {
            Template template = document.getTemplate();
            if (template != null) {
                Long maxTempNo = documentRepository.findMaxTempNoByTemplate(template);
                document.setTempNo(maxTempNo + 1L);
            }
        } else if (document.getStatus() == 0) {
            document.setDno(null);
        }
        return documentRepository.save(document);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
//    public Document findDocumentBySno(Long Sno) { return documentRepository.findBySno(Sno); }

//    public void deleteEditDocument(Long sno) {
//        Document document = documentRepository.findBySno(sno);
//        if (document != null) {
//            // 문서에 속한 첨부파일들을 삭제
//            List<Attachment> attachments = attachmentService.getAttachmentsByDocumentId(document.getId());
//            for (Attachment attachment : attachments) {
//                attachmentService.deleteAttachment(attachment.getId());
//            }
//            // 문서 삭제
//            documentRepository.deleteBySno(sno);
//        } else {
//            throw new RuntimeException("해당 문서를 찾을 수 없습니다.");
//        }
//    }

//    @Override
//    public Document getDocumentById(Long id) {
//        Optional<Document> documentOptional = documentRepository.findById(id);
//        return documentOptional.orElseThrow(() -> new RuntimeException("해당 문서를 찾을 수 없습니다."));
//    }
//
//    @Override
//    public Document findDocumentById(Long id) {
//        return documentRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("해당 문서를 찾을 수 없습니다."));
//    }
//
////    @Override
//    public void deleteAttachment(Long id) {
//        attachmentService.deleteAttachment(id);
//    }

    @Override
    public Page<Document> findDocumentListByStatusNot(int status, Pageable pageable) {
        return documentRepository.findByStatusNot(status, pageable);
    }

    @Override
    public void setWriterByToken(Document document) {

        // 멤버 아이디를 이용하여 데이터베이스에서 멤버 정보를 조회합니다.
        Member writer = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다."));

        // 문서의 작성자를 설정합니다.
        document.setWriter(writer);
    }

    @Override
    public Page<Document> findDocumentListByWriterAndStatus(Long memberId, int status, Pageable pageable) {
        return documentRepository.findByWriterIdAndStatus(memberId, status, pageable);
    }

    @Override
    public Page<Document> findDocumentListByTemplateIdAndStatus(Long id, int status, Pageable pageable) {
        return documentRepository.findByTemplateIdAndStatus(id, status, pageable);
    }

    @Override
    public Page<Document> findDocumentListByWriterAndTemplateIdAndStatus(Long memberId, Long id, int status, Pageable pageable) {
        return documentRepository.findByWriterAndTemplateIdAndStatus(memberId, id, status, pageable);
    }

    @Override
    public List<Document> getApprovedDocuments() {
        List<Document> allDocs = documentRepository.findAll();

        return allDocs.stream()
                .filter(document -> !document.getResult().equals("진행중") && document.getAppDate() != null)
                .collect(Collectors.toList());
    }


    @Override
    public Page<Document> findDocumentListByWriterAndStatusAndResult(Long id, int status, String result, Pageable pageable) {
        return documentRepository.findByWriterIdAndStatusAndResult (id, status, result, pageable);
    }


//    public Document getDocumentById(Long documentId) {
//        // findById 메서드는 Optional<Document>를 반환한다고 가정합니다.
//        Document document = documentRepository.findById(documentId);
//        return document;
//
////        // 문서가 찾아지지 않은 경우를 처리할 수 있습니다.
////        // 여기에서는 orElseThrow를 사용하여 문서를 찾지 못한 경우 예외를 던집니다.
////        return optionalDocument.orElseThrow(() -> new RuntimeException("ID " + documentId + "에 해당하는 문서를 찾을 수 없습니다."));
//    }

    public Document getDocumentById(Long documentId) {
        return documentRepository.findDocumentWithTemplateById(documentId);
    }



}


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
import org.springframework.stereotype.Service;

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

    @Override
    public List<Document> findDocumentListByStatusNot(int status) {
        return documentRepository.findByStatusNot(status);
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
    public List<Document> findDocumentListByWriterAndStatus(Long memberId, int status) {
        return documentRepository.findByWriterIdAndStatus(memberId, status);
    }

    @Override
    public List<Document> findDocumentListByTemplateIdAndStatus(Long id, int status) {
        return documentRepository.findByTemplateIdAndStatus(id, status);
    }

    @Override
    public List<Document> findDocumentListByWriterAndTemplateIdAndStatus(Long memberId, Long id, int status) {
        return documentRepository.findByWriterAndTemplateIdAndStatus(memberId, id, status);
    }

    @Override
    public List<Document> getApprovedDocuments() {
        List<Document> allDocs = documentRepository.findAll();

        return allDocs.stream()
                .filter(document -> document.getAppDate() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Document> findDocumentListByWriterAndStatusAndResult(Long id, int status, String result) {
        return documentRepository.findByWriterIdAndStatusAndResult(id, status, result);
    }

    public Document getDocumentById(Long documentId) {
        return documentRepository.findDocumentWithTemplateById(documentId);
    }


}


package com.groupware.wimir.service;

import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.AppRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.TemplateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final MemberRepository memberRepository;
    private final TemplateRepository templateRepository;
    private final AppRepository appRepository;

    public DocumentService(DocumentRepository documentRepository, MemberRepository memberRepository, TemplateRepository templateRepository, AppRepository appRepository) {
        this.documentRepository = documentRepository;
        this.memberRepository = memberRepository;
        this.templateRepository = templateRepository;
        this.appRepository = appRepository;
    }

    // 문서 작성
    public Document createDocument(Document document) {
        return documentRepository.save(document);
    }

    // 문서 수정
    public Document updateDocument(Document updatedDocument) {
        Document document = documentRepository.findById(updatedDocument.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서를 찾을 수 없습니다."));

        // 기존 문서의 필드들을 업데이트
        document.setTitle(updatedDocument.getTitle());
        document.setContent(updatedDocument.getContent());
        document.setApp(updatedDocument.getApp());

        // memberId에 해당하는 Member 객체를 가져옵니다.
        Member member = memberRepository.findById(updatedDocument.getMember().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "직원을 찾을 수 없습니다."));
        document.setMember(member);

        // appId에 해당하는 App 객체를 가져옵니다.
        App app = appRepository.findById(updatedDocument.getApp().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "결재를 찾을 수 없습니다."));
        document.setApp(app);

        // temId에 해당하는 Template 객체를 가져옵니다.
        Template template = templateRepository.findById(updatedDocument.getTemplate().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서 양식을 찾을 수 없습니다."));
        document.setTemplate(template);

        return documentRepository.save(document);
    }

    // 문서 삭제
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    // 문서 조회
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서를 찾을 수 없습니다."));
    }

    // 문서 리스트 조회
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

}

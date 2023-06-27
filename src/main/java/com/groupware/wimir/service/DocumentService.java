package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    // 문서 저장
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    // 모든 문서 조회
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    // 문서 조회
    public Document getDocumentById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서를 찾을 수 없습니다."));
    }

    // 문서 삭제
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    // 문서 업데이트
    public Document updateDocument(Document updatedDocument) {
        Document existingDocument = documentRepository.findById(updatedDocument.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "문서를 찾을 수 없습니다."));

        // 기존 문서의 필드들을 업데이트
        existingDocument.setTitle(updatedDocument.getTitle());
        existingDocument.setContent(updatedDocument.getContent());
        existingDocument.setWriter(updatedDocument.getWriter());

        return documentRepository.save(existingDocument);
    }
}

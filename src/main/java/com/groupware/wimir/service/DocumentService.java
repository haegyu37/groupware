package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public Document saveDocument(Document document) {
        // 문서 저장 로직 수행
        return documentRepository.save(document);
    }

    public List<Document> getAllDocuments() {
        // 모든 문서 조회 로직 수행
        return documentRepository.findAll();
    }

//    public Document getDocumentById(Long id) {
//        // 문서 조회 로직 수행
//        return documentRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다."));
//    }

    public void deleteDocument(Long id) {
        // 문서 삭제 로직 수행
        documentRepository.deleteById(id);
    }

//    public Document updateDocument(Document updatedDocument) {
//        // 문서 수정 로직 수행
//        Document existingDocument = documentRepository.findById(updatedDocument.getId())
//                .orElseThrow(() -> new NotFoundException("문서를 찾을 수 없습니다."));
//
//        // 기존 문서의 필드들을 업데이트
//        existingDocument.setTitle(updatedDocument.getTitle());
//        existingDocument.setContent(updatedDocument.getContent());
//        existingDocument.setWriter(updatedDocument.getWriter());
//
//        return documentRepository.save(existingDocument);
//    }
}

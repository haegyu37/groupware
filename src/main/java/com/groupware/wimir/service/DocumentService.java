package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DocumentService {

//    Document findDocumentById(Long id);

//    List<Document> findSaveDocumentList();

    Document findDocumentById(Long id);

    Document saveDocument(Document document);

    void deleteDocument(Long id);

    public List<Document> findDocumentListByStatusNot(int status);

    void setWriterByToken(Document document);

    List<Document> findDocumentListByWriterAndStatus(Long memberId, int status);

    List<Document> findDocumentListByTemplateIdAndStatus(Long id, int status);

    List<Document> findDocumentListByWriterAndTemplateIdAndStatus(Long memberId, Long id, int status);

    List<Document> getApprovedDocuments();

    List<Document> findDocumentListByWriterAndStatusAndResult(Long currentMemberId, int i, String 승인);


//    List<Document> getDocumentsByCategory(String category);
//
//    List<Document> getDocumentsByTemplate(Template template);


//    public Document getDocumentById(Long documentId);

//    Document getDocumentById(Long documentId);
//
//    Page<Document> findDocumentListByWriter(Member writer, Pageable pageable);

}

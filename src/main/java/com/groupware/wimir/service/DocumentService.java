package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DocumentService {

//    Document findDocumentById(Long id);

//    List<Document> findSaveDocumentList();

    Document findDocumentById(Long id);

    Document findDocumentBySno(Long sno);

    Document saveDocument(Document document);

    void deleteDocument(Long id);

    public Page<Document> findDocumentListByStatusNot(int status, Pageable pageable);

    void setWriterByToken(Document document);

    Page<Document> findDocumentListByWriterAndStatus(Long memberId, int status, Pageable pageable);

    Page<Document> findDocumentListByTemplateIdAndStatus(Long id, int status, Pageable pageable);

    Page<Document> findDocumentListByWriterAndTemplateIdAndStatus(Long memberId, Long id, int status, Pageable pageable);

    List<Document> getApprovedDocuments();

    Page<Document> findDocumentListByWriterAndStatusAndResult(Long id, int status, String result, Pageable pageable);


//    List<Document> getDocumentsByCategory(String category);
//
//    List<Document> getDocumentsByTemplate(Template template);


//    public Document getDocumentById(Long documentId);

//    Document getDocumentById(Long documentId);
//
//    Page<Document> findDocumentListByWriter(Member writer, Pageable pageable);

}

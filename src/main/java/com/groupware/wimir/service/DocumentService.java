package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DocumentService {

    Page<Document> findDocumentList(Pageable pageable);

    Document findDocumentByDno(Long dno);

    List<Document> findSaveDocumentList();

    void saveDocument(Document document);

    Document findDocumentBySno(Long Sno);

    void deleteEditDocument(Long sno);

    void deleteDocument(Long dno);
}
package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface DocumentService {

    Page<Document> findDocumentList(Pageable pageable);
    Document findDocumentById(Long id);

    List<Document> findSaveDocumentList();

    void saveDocument(Document document);

    Document findDocumentBySaveId(Long SaveId);

}

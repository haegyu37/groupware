package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface DocumentService {

    Document findDocumentById(Long id);

    List<Document> findSaveDocumentList();

    Document saveDocument(Document document);

    void deleteDocument(Long id);

    public Page<Document> findDocumentListByStatusNot(int status, Pageable pageable);


    void setWriterByToken(Document document);
}
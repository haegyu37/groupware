package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
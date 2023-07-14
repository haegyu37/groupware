package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Page<Document> findDocumentList(Pageable pageable) {
        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
                pageable.getPageSize());
        return documentRepository.findAll(pageable);
    }

    @Override
    public Document findDocumentById(Long id) {
        return documentRepository.findById(id).orElse(new Document());
    }
}

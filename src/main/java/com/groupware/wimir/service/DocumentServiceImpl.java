package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Document> findSaveDocumentList() {
        return documentRepository.findByStatus(0); // 임시저장 상태인 문서 조회
    }

    @Override
    public void saveDocument(Document document) {
        if (document.getStatus() == 0) {
            // 임시저장 상태인 경우 id는 null
            document.setId(null);
        }
        documentRepository.save(document);
    }

    @Override
    public Document findDocumentBySaveId(Long SaveId) { return documentRepository.findBySaveId(SaveId); }

}

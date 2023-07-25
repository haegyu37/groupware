package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public Document findDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public List<Document> findSaveDocumentList() {
        return documentRepository.findByStatus(0);
    }

    @Override
    public Document saveDocument(Document document) {
        if (document.getStatus() == 0) {
            // 임시저장 상태인 경우 id는 null
            document.setDno(null);
        }
        return documentRepository.save(document);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public Page<Document> findDocumentListByStatusNot(int status, Pageable pageable) {
        return documentRepository.findByStatusNot(status, pageable);
    }


}
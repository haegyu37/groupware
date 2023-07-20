package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
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
    public Document findDocumentByDno(Long dno) {
        return documentRepository.findByDno(dno).orElse(new Document());
    }

    public void deleteDocument(Long dno) {
        documentRepository.deleteByDno(dno);
    }

    @Override
    public List<Document> findSaveDocumentList() {
        return documentRepository.findByStatus(0); // 임시저장 상태인 문서 조회
    }

    @Override
    public void saveDocument(Document document) {
        if (document.getStatus() == 0) {
            // 임시저장 상태인 경우 id는 null
            document.setDno(null);
        }
        documentRepository.save(document);
    }

    @Override
    public Document findDocumentBySno(Long Sno) { return documentRepository.findBySno(Sno); }

    public void deleteEditDocument(Long sno) {
        documentRepository.deleteBySno(sno);
    }


}
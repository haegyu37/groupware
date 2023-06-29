package com.groupware.wimir;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class DocumentTest {

    @Autowired
    DocumentRepository documentRepository;

//    @Test
//    void saveDocument() {
//        Document document = new Document();
//        document.setId(2L);
//        document.setTitle("dfd");
//        document.setContent("dffd");
//        document.setWriter("dfd");
//        document.setTem(2L);
//        document.setWrittenDate(LocalDateTime.now());
//        documentRepository.save(document);
//    }

//    @Test
//    void deleteDocument() {
//        Document document = new Document();
//
//        Long documentId = 9L;
//
//        documentRepository.deleteById(documentId);
//    }

//    @Test
//    void updateDocument() {
//        Document document = documentRepository.findById(1L).orElse(null);
//
//        if (document != null) {
//            document.setTitle("수정");
//            document.setContent("dgfg");
//            document.setWriter("dgfdgd");
//            document.setTem(3L);
//            document.setWrittenDate(LocalDateTime.now());
//
//            documentRepository.save(document);
//        }
//    }

//    @Test
//    void getAllDocuments() {
//
//        // 모든 문서 조회
//        List<Document> documents = documentRepository.findAll();
//        assertTrue(!documents.isEmpty());
//    }

//    @Test
//    void getDocument() {
//        // 문서 ID로 조회
//        Document document = documentRepository.getDocumentById(2L);
//
//        // 검증
//        assertNotNull(document);
//        assertEquals(2L, document.getId());
//    }
}

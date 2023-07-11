package com.groupware.wimir;

import com.groupware.wimir.controller.AppController;
import com.groupware.wimir.controller.DocumentController;
import com.groupware.wimir.dto.DocumentDTO;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
public class DocumentTest {

    @Autowired
    private DocumentController documentController;


//    @Test
//    void saveDocument() {
//        Document document = new Document();
//        document.setId(3L);
//        document.setTitle("ㄹㄹㅈㄷ");
//        document.setContent("dff");
//        document.setWrittenDate(LocalDateTime.now());
////        document.setMember(21L);
////        document.setTem(2L);
////        document.setApp(12L);
//        documentRepository.save(document);
//    }

//    @Test
//    void createDocument() {
//        DocumentDTO documentDTO = new DocumentDTO();
//        documentDTO.setTitle("ㄹㄹㅈㄷ");
//        documentDTO.setContent("dff");
//        documentDTO.setWrittenDate(LocalDateTime.now());
//        documentDTO.setMemberId(1L); // memberId 값 설정
//        documentDTO.setAppId(1L);
//        documentDTO.setTemId(1L);
//
//        ResponseEntity<Document> response = documentController.createDocument(documentDTO);
//        Document savedDocument = response.getBody();
//        assertNotNull(savedDocument);
//    }


//    @Test
//    void deleteDocument() {
//        Document document = new Document();
//
//        Long documentId = 1L;
//
//        documentRepository.deleteById(documentId);
//    }

//    @Test
//    void updateDocument() {
//        Long documentId = 2L; // 수정할 문서의 id
//
//        // 기존 문서 조회
//        Document document = documentRepository.findById(documentId)
//                .orElseThrow(() -> new NoSuchElementException("문서를 찾을 수 없습니다."));
//
//        // 필드 수정
//        document.setTitle("수정된 제목");
//        document.setContent("수정된 내용");
//        document.setWrittenDate(LocalDateTime.now());
//
//        // 수정된 문서 저장
//        documentRepository.save(document);
//
//        // 수정된 문서 확인
//        Document updatedDocument = documentRepository.findById(documentId)
//                .orElseThrow(() -> new NoSuchElementException("수정된 문서를 찾을 수 없습니다."));
//
//        // 수정된 정보 출력 또는 어서션 등을 사용하여 확인
//        System.out.println("수정된 제목: " + updatedDocument.getTitle());
//        System.out.println("수정된 내용: " + updatedDocument.getContent());
//        System.out.println("수정된 작성일자: " + updatedDocument.getWrittenDate());
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

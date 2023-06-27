package com.groupware.wimir;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class DocTest {
    @Mock
    private DocumentRepository documentRepository;

    private DocumentService documentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        documentService = new DocumentService(documentRepository);
    }

    @Test
    void saveDocument() {
        // given
        Document document = new Document();
        when(documentRepository.save(document)).thenReturn(document);

        // when
        Document savedDocument = documentService.saveDocument(document);

        // then
        assertEquals(document, savedDocument);
        verify(documentRepository, times(1)).save(document);
    }

    @Test
    void getAllDocuments() {
        // given
        List<Document> documents = Arrays.asList(new Document(), new Document());
        when(documentRepository.findAll()).thenReturn(documents);

        // when
        List<Document> result = documentService.getAllDocuments();

        // then
        assertEquals(documents, result);
        verify(documentRepository, times(1)).findAll();
    }

    @Test
    void getDocumentById_existingDocument() {
        // given
        Long documentId = 1L;
        Document document = new Document();
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(document));

        // when
        Document result = documentService.getDocumentById(documentId);

        // then
        assertEquals(document, result);
        verify(documentRepository, times(1)).findById(documentId);
    }

    @Test
    void getDocumentById_nonExistingDocument() {
        // given
        Long documentId = 1L;
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(ResponseStatusException.class, () -> documentService.getDocumentById(documentId));
        verify(documentRepository, times(1)).findById(documentId);
    }

    @Test
    void deleteDocument() {
        // given
        Long documentId = 1L;

        // when
        documentService.deleteDocument(documentId);

        // then
        verify(documentRepository, times(1)).deleteById(documentId);
    }

    @Test
    void updateDocument_existingDocument() {
        // given
        Document updatedDocument = new Document();
        updatedDocument.setId(1L);
        updatedDocument.setTitle("Updated Title");
        updatedDocument.setContent("Updated Content");
        updatedDocument.setWriter("Updated Writer");

        Document existingDocument = new Document();
        existingDocument.setId(1L);
        existingDocument.setTitle("Original Title");
        existingDocument.setContent("Original Content");
        existingDocument.setWriter("Original Writer");

        when(documentRepository.findById(updatedDocument.getId())).thenReturn(Optional.of(existingDocument));
        when(documentRepository.save(existingDocument)).thenReturn(existingDocument);

        // when
        Document result = documentService.updateDocument(updatedDocument);

        // then
        assertEquals(updatedDocument.getTitle(), result.getTitle());
        assertEquals(updatedDocument.getContent(), result.getContent());
        assertEquals(updatedDocument.getWriter(), result.getWriter());
        verify(documentRepository, times(1)).findById(updatedDocument.getId());
        verify(documentRepository, times(1)).save(existingDocument);
    }

    @Test
    void updateDocument_nonExistingDocument() {
        // given
        Document updatedDocument = new Document();
        updatedDocument.setId(1L);
        updatedDocument.setTitle("Updated Title");
        updatedDocument.setContent("Updated Content");
        updatedDocument.setWriter("Updated Writer");

        when(documentRepository.findById(updatedDocument.getId())).thenReturn(Optional.empty());

        // when, then
        assertThrows(ResponseStatusException.class, () -> documentService.updateDocument(updatedDocument));
        verify(documentRepository, times(1)).findById(updatedDocument.getId());
        verify(documentRepository, never()).save(any(Document.class));
    }
}


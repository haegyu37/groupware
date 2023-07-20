package com.groupware.wimir.service;

import com.groupware.wimir.entity.Attachment;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.AttachmentRepository;
import com.groupware.wimir.repository.DocumentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final Path attachmentStorageLocation;
    private final DocumentRepository documentRepository;

    public AttachmentService(AttachmentRepository attachmentRepository, DocumentRepository documentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.documentRepository = documentRepository;
        // 파일 저장 디렉토리 설정 및 생성 코드 생략
        this.attachmentStorageLocation = Paths.get("C:\\groupware\\upload");
    }

    public Long uploadAttachment(MultipartFile file, Long documentId) {
        String name = getName(file);
        Path targetLocation = this.attachmentStorageLocation.resolve(name);

        // 문서 id에 해당하는 Document 엔티티를 찾아옴
        Document document = findDocumentById(documentId);

        Attachment newAttachment = Attachment.builder()
                .originalName(name)
                .savedName(name)
                .size(file.getSize())
                .path(targetLocation.getParent().toString())
                .document(document) // 문서 정보를 설정해줌
                .build();
        attachmentRepository.save(newAttachment);
        return newAttachment.getId();
    }

    private String getName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            return StringUtils.cleanPath(originalFilename);
        } else {
            return file.getName();
        }
    }

    public Attachment getAttachmentById(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        return attachmentOptional.orElseThrow(() -> new RuntimeException("해당 첨부파일을 찾을 수 없습니다."));
    }

    public byte[] downloadAttachment(Long id) {
        Attachment attachment = getAttachmentById(id);
        Path attachmentPath = Paths.get(attachment.getPath(), attachment.getSavedName());
        try {
            return Files.readAllBytes(attachmentPath);
        } catch (IOException ex) {
            throw new RuntimeException("첨부파일을 다운로드할 수 없습니다.", ex);
        }
    }

    public void deleteAttachment(Long id) {
        Attachment attachment = getAttachmentById(id);
        Path attachmentPath = Paths.get(attachment.getPath(), attachment.getSavedName());
        try {
            Files.delete(attachmentPath);
            attachmentRepository.delete(attachment);
        } catch (IOException ex) {
            throw new RuntimeException("첨부파일을 삭제할 수 없습니다.", ex);
        }
    }

    public void saveAttachments(List<Attachment> attachments, Long documentId) {
        Document document = new Document();
        document.setId(documentId);

        for (Attachment attachment : attachments) {
            attachment.setDocument(document);
        }
        attachmentRepository.saveAll(attachments);
    }

    private Document findDocumentById(Long documentId) {
        Optional<Document> documentOptional = documentRepository.findById(documentId);
        return documentOptional.orElseThrow(() -> new RuntimeException("해당 문서를 찾을 수 없습니다."));
    }
}

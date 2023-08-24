package com.groupware.wimir.service;

import com.groupware.wimir.entity.Attachment;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.AttachmentRepository;
import com.groupware.wimir.repository.DocumentRepository;
import org.apache.commons.io.FilenameUtils;
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

    // 첨부파일 저장 경로(현재 컴퓨터의 다운로드 폴더)
    private static final String DOWNLOAD_PATH = "C:\\Users\\codepc\\Downloads";

    public AttachmentService(AttachmentRepository attachmentRepository, DocumentRepository documentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.documentRepository = documentRepository;

        // 파일 저장 디렉토리 설정
        String uploadPath = "C:\\uploads";
        this.attachmentStorageLocation = Paths.get(uploadPath);

        // 파일 저장 디렉토리 생성
        try {
            Files.createDirectories(this.attachmentStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 디렉토리를 생성할 수 없습니다.", e);
        }
    }

    // 첨부파일의 내용을 바이트로 가져오는 메서드
    public byte[] downloadAttachmentFileBytes(Long id) throws IOException {
        Attachment attachment = getAttachmentById(id);
        Path attachmentPath = Paths.get(DOWNLOAD_PATH, attachment.getSavedName());
        return Files.readAllBytes(attachmentPath);
    }


    //  파일 이름 중복시 새로 이름 부여
    private String getUniqueFileName(String originalFileName) {
        String baseName = FilenameUtils.getBaseName(originalFileName);
        String extension = FilenameUtils.getExtension(originalFileName);
        String timestamp = Long.toString(System.currentTimeMillis());

        String uniqueFileName = baseName + "_" + timestamp + "." + extension;
        while (Files.exists(this.attachmentStorageLocation.resolve(uniqueFileName))) {
            timestamp = Long.toString(System.currentTimeMillis());
            uniqueFileName = baseName + "_" + timestamp + "." + extension;
        }
        return uniqueFileName;
    }

    public Long uploadAttachment(MultipartFile file, Long documentId) {
        String originalFileName = file.getOriginalFilename();
        String savedFileName = getUniqueFileName(originalFileName); // 저장 파일 이름 생성

        Path targetLocation = this.attachmentStorageLocation.resolve(savedFileName);

        // 문서 id에 해당하는 Document 엔티티를 찾아옴
        Document document = findDocumentById(documentId);

        Attachment newAttachment = Attachment.builder()
                .originalName(originalFileName) // 원본 파일 이름 설정
                .savedName(savedFileName) // 저장 파일 이름 설정
                .size(file.getSize())
                .path(targetLocation.getParent().toString())
                .document(document)
                .build();
        attachmentRepository.save(newAttachment);
        return newAttachment.getId();
    }

    public Attachment getAttachmentById(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        return attachmentOptional.orElseThrow(() -> new RuntimeException("해당 첨부파일을 찾을 수 없습니다."));
    }

    private Document findDocumentById(Long documentId) {
        Optional<Document> documentOptional = documentRepository.findById(documentId);
        return documentOptional.orElseThrow(() -> new RuntimeException("해당 문서를 찾을 수 없습니다."));
    }

    //문서번호로 첨부파일 삭제
    public void deleteAttachmentByDoc(Long id) {
        List<Attachment> attachments = attachmentRepository.findByDocumentId(id);
        for (Attachment attachment : attachments) {
            attachmentRepository.delete(attachment);
        }
    }
}

package com.groupware.wimir.service;

import com.groupware.wimir.entity.Attachment;
import com.groupware.wimir.repository.AttachmentRepository;
import org.springframework.stereotype.Service;
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

    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
        // 파일 저장 디렉토리 설정 및 생성 코드 생략
        this.attachmentStorageLocation = Paths.get("C:\\groupware\\upload");
    }

    public Long uploadAttachment(MultipartFile file, Long docId, String writter) {
        String name = getName(file);
        try {
            // 파일 업로드 경로 설정
            Path targetLocation = this.attachmentStorageLocation.resolve(name);
            // 파일 업로드
            Files.createDirectories(targetLocation.getParent()); // 부모 디렉터리 생성
            Files.copy(file.getInputStream(), targetLocation);

            // Attachment 생성 및 저장
            Attachment newAttachment = new Attachment();
            newAttachment.setName(name);
            newAttachment.setPath(targetLocation.getParent().toString()); // 파일 경로 설정
            newAttachment.setSize(file.getSize()); // 파일 크기 설정
            newAttachment.setDocId(docId);
            newAttachment.setWritter(writter); // 작성자 정보 설정
            attachmentRepository.save(newAttachment);

            return newAttachment.getId(); // 저장된 첨부 파일 ID 반환
        } catch (IOException ex) {
            // 파일 업로드 중 오류 발생 시 예외 처리
            throw new RuntimeException("첨부파일을 업로드할 수 없습니다.", ex);
        }
    }

    private String getName(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (StringUtils.hasText(originalFilename)) {
            return StringUtils.cleanPath(originalFilename);
        } else {
            return file.getName();
        }
    }

    public byte[] downloadAttachment(Long id) {
        Attachment attachment = getAttachmentById(id);
        Path attachmentPath = Paths.get(attachment.getAttachmentLocation());
        try {
            return Files.readAllBytes(attachmentPath);
        } catch (IOException ex) {
            throw new RuntimeException("첨부파일을 다운로드할 수 없습니다.", ex);
        }
    }

    public boolean deleteAttachment(Long id, String currentUser) {
        Attachment attachment = getAttachmentById(id);

        // 첨부 파일을 업로드한 사용자와 현재 로그인한 사용자를 비교하여 권한 확인
        if (!attachment.getWritter().equals(currentUser)) {
            throw new RuntimeException("해당 첨부파일을 삭제할 권한이 없습니다.");
        }

        try {
            // Attachment 삭제
            attachmentRepository.delete(attachment); // 데이터베이스에서 첨부파일 정보 삭제

            return true;
        } catch (Exception ex) {
            throw new RuntimeException("첨부파일을 삭제하는 중에 오류가 발생했습니다.", ex);
        }
    }

//    public Path getAttachmentPathById(Long id) {
//        Attachment attachment = getAttachmentById(id);
//        return Paths.get(attachment.getAttachmentLocation());
//    }

    public Attachment getAttachmentById(Long id) {
        Optional<Attachment> attachmentOptional = attachmentRepository.findById(id);
        return attachmentOptional.orElseThrow(() -> new RuntimeException("해당 첨부파일을 찾을 수 없습니다."));
    }
}

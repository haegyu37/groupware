package com.groupware.ahnkookyukyu.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileStorageLocation;

    public FileServiceImpl() {
        // 파일 저장 위치 설정
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장할 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    @Override
    public void uploadFile(MultipartFile file, Long documentId) {
        // 파일 업로드 로직 구현
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // 파일 저장 경로 설정
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // 파일 저장
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장할 수 없습니다.", ex);
        }
        // 문서와 파일 매핑 등 추가 로직
    }

    @Override
    public Resource downloadFile(Long fileId) {
        // 파일 다운로드 로직 구현
        // 파일 리소스 반환
        return null;
    }

    @Override
    public void deleteFile(Long fileId) {
        // 파일 삭제 로직 구현
        // 파일 삭제
    }
}

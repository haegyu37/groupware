package com.groupware.wimir.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileServiceImpl implements FileService {

    private final Path fileStorageLocation;

    // 파일 저장 위치 설정
    public FileServiceImpl() {
        this.fileStorageLocation = Paths.get("uploads").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장할 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    // 파일 업로드
    @Override
    public boolean uploadFile(MultipartFile file, Long documentId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // 파일 저장 경로 설정
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // 파일 저장
            Files.copy(file.getInputStream(), targetLocation);
        } catch (IOException ex) {
            throw new RuntimeException("파일을 저장할 수 없습니다.", ex);
        }
        return false;
    }

    // 파일 다운로드
    @Override
    public byte[] downloadFile(Long fileId) {
        return null;
    }

    // 파일 삭제
    @Override
    public boolean deleteFile(Long fileId) {
        return false;
    }
}

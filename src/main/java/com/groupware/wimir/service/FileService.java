package com.groupware.wimir.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private final Path fileStorageLocation;

    // 파일 업로드 위치 설정
    public FileService() {
        // 파일 저장 디렉토리 설정
        this.fileStorageLocation = Paths.get("upload").toAbsolutePath().normalize();
        try {
            // 파일 저장 디렉토리 생성
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            // 디렉토리 생성 중 오류 발생 시 예외 처리
            throw new RuntimeException("파일을 저장할 디렉토리를 생성할 수 없습니다.", ex);
        }
    }

    // 파일 업로드
    public boolean uploadFile(MultipartFile file, Long documentId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            // 파일 업로드 경로 설정
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            // 파일 업로드
            Files.copy(file.getInputStream(), targetLocation);
            return true; // 파일 업로드 성공 시 true 반환
        } catch (IOException ex) {
            // 파일 업로드 중 오류 발생 시 예외 처리
            throw new RuntimeException("파일을 업로드할 수 없습니다.", ex);
        }
    }

    // 파일 다운로드
    public byte[] downloadFile(Long fileId) {
        Path filePath = getFileById(fileId); // 파일 ID에 해당하는 파일 경로를 얻어온다.
        try {
            return Files.readAllBytes(filePath); // 파일을 byte 배열로 읽어서 반환한다.
        } catch (IOException ex) {
            throw new RuntimeException("파일을 다운로드할 수 없습니다.", ex);
        }
    }

    // 파일 삭제
    public boolean deleteFile(Long fileId) {
        Path filePath = getFileById(fileId); // 파일 ID에 해당하는 파일 경로를 얻어온다.
        try {
            Files.deleteIfExists(filePath); // 파일을 삭제한다.
            return true; // 파일 삭제 성공 시 true 반환
        } catch (IOException ex) {
            throw new RuntimeException("파일을 삭제할 수 없습니다.", ex);
        }
    }

    private Path getFileById(Long fileId) {
        // 파일 ID에 해당하는 파일 경로를 가져와야 합니다.
        String fileName = fileId + ".pdf"; // 예시로 파일 확장자는 .pdf 가정합니다.
        return fileStorageLocation.resolve(fileName);
    }

    public String getFileNameById(Long fileId) {
        return "original_file_name.ext";
    }

}
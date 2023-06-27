package com.groupware.wimir.controller;

import com.groupware.wimir.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    // 첨부파일 업로드
    @PostMapping("/files")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam("documentId") Long documentId) {
        boolean isUploaded = fileService.uploadFile(file, documentId);

        if (isUploaded) {
            return ResponseEntity.ok("File uploaded successfully");
        } else {
            // 파일 업로드 실패에 대한 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }


    // 첨부파일 다운로드
    @GetMapping("/files/{fileId}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileId") Long fileId) {
        byte[] fileBytes = fileService.downloadFile(fileId);

        if (fileBytes != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "filename.ext"); // 파일 이름과 확장자를 지정하세요

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } else {
            // 파일이 존재하지 않는 경우에 대한 처리
            return ResponseEntity.notFound().build();
        }
    }

    // 첨부파일 삭제
    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long fileId) {
        boolean isDeleted = fileService.deleteFile(fileId);

        if (isDeleted) {
            return ResponseEntity.ok("File deleted successfully");
        } else {
            // 파일 삭제 실패에 대한 처리
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file");
        }
    }

}


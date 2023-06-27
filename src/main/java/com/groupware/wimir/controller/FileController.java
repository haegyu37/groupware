package com.groupware.wimir.controller;

import com.groupware.wimir.service.FileService;
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
        fileService.uploadFile(file, documentId);
        return ResponseEntity.ok("File uploaded successfully");
    }

//    // 첨부파일 다운로드
//    @GetMapping("/files/{fileId}")
//    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") Long fileId) {
//        Resource fileResource = fileService.downloadFile(fileId);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getFilename() + "\"")
//                .body(fileResource);
//    }

    // 첨부파일 삭제
    @DeleteMapping("/files/{fileId}")
    public ResponseEntity<String> deleteFile(@PathVariable("fileId") Long fileId) {
        fileService.deleteFile(fileId);
        return ResponseEntity.ok("File deleted successfully");
    }
}


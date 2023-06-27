package com.groupware.wimir.service;

import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

public interface FileService {
    void uploadFile(MultipartFile file, Long documentId);
    Resource downloadFile(Long fileId);
    void deleteFile(Long fileId);
}

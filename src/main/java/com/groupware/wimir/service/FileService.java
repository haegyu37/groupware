package com.groupware.wimir.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    boolean uploadFile(MultipartFile file, Long documentId);
    byte[] downloadFile(Long fileId);
    boolean deleteFile(Long fileId);
}

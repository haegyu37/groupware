package com.groupware.wimir.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class FileService {

    public String uploadFile(String uploadPath, String oriName, byte[] fileData) throws IOException {
        UUID uuid = UUID.randomUUID();
        String extension = oriName.substring(oriName.lastIndexOf("."));
        String savedName = uuid.toString() + extension;
        String fileUploadUrl = uploadPath + "/" + savedName;

        // 업로드 폴더가 없는 경우 폴더 생성
        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs(); // 폴더를 생성합니다.
        }

        FileOutputStream fos = new FileOutputStream(fileUploadUrl);
        fos.write(fileData);
        fos.close();
        return savedName;
    }

    public void deleteFile(String filePath) throws Exception {
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일 삭제함");
        } else {
            log.info("파일 존재 X");
        }
    }


}
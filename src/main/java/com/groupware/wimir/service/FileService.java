package com.groupware.wimir.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Log
public class FileService {

    public String uploadFile(String uploadPath, String oriName, byte[] fileData) throws Exception {
        UUID uuid = UUID.randomUUID();
        String extension = oriName.substring(oriName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;

        // 폴더 생성 코드 추가
        File uploadFolder = new File(uploadPath);
        if (!uploadFolder.exists()) {
            boolean folderCreated = uploadFolder.mkdirs();
            if (folderCreated) {
                System.out.println("폴더 생성 성공: " + uploadPath);
            } else {
                System.err.println("폴더 생성 실패: " + uploadPath);
            }
        } else {
            System.out.println("폴더 이미 존재함: " + uploadPath);
        }

        FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);
        fos.write(fileData);
        fos.close();
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);

        if(deleteFile.exists()){
            deleteFile.delete();
            log.info("파일 삭제함");
        } else {
            log.info("파일 존재 X");
        }
    }

}

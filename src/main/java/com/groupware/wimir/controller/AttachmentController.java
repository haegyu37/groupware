package com.groupware.wimir.controller;

import com.groupware.wimir.service.AttachmentService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/attachments")
public class AttachmentController {
    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    // 첨부파일 업로드
    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadAttachments(@RequestParam("attachments") List<MultipartFile> files,
                                                    @RequestParam("documentId") Long documentId) {
        try {
            List<Long> attachmentIds = new ArrayList<>();
            for (MultipartFile file : files) {
                Long attachmentId = attachmentService.uploadAttachment(file, documentId);
                attachmentIds.add(attachmentId);
            }
            String response = "첨부파일이 업로드되었습니다. 첨부파일 ID 목록 : " + attachmentIds;
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "첨부파일 업로드를 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 첨부파일 다운로드
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id) {
        try {
            byte[] fileBytes = attachmentService.downloadAttachment(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "attachment");
            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 첨부파일 삭제
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteAttachment(@PathVariable Long id) {
        try {
            attachmentService.deleteAttachment(id);
            String response = "첨부파일이 삭제되었습니다. 첨부파일 ID : " + id;
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "첨부파일 삭제를 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}




package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Attachment;
import com.groupware.wimir.repository.AttachmentRepository;
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
    private AttachmentService attachmentService;
    private AttachmentRepository attachmentRepository;

    // 첨부파일 업로드
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadAttachments(@RequestParam("attachments") List<MultipartFile> files,
                                                    @RequestParam("documentId") Long documentId) {
        try {
            List<Long> attachmentIds = new ArrayList<>();
            for (MultipartFile file : files) {
                Long attachmentId = attachmentService.uploadAttachment(file, documentId);
                attachmentIds.add(attachmentId);
            }
            String response = "첨부파일이 업로드되었습니다. 첨부파일 ID : " + attachmentIds;
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            String errorResponse = "첨부파일 업로드를 실패했습니다.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // 첨부파일 다운로드(오류는 없으나, postman에서는 확인 불가능)
    @GetMapping(value = "/download/{id}")
    public ResponseEntity<byte[]> downloadAttachment(@PathVariable Long id, @RequestParam(required = false) String savedFileName) {
        try {
            Attachment attachment = attachmentService.getAttachmentById(id);
            byte[] fileBytes = attachmentService.downloadAttachmentFileBytes(id);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            if (savedFileName == null) {
                // 사용자가 파일 이름을 지정하지 않은 경우 기본적으로 원본 파일명으로 저장
                headers.setContentDispositionFormData("attachment", attachment.getOriginalName());
            } else {
                // 사용자가 파일 이름을 지정한 경우 해당 이름으로 저장
                headers.setContentDispositionFormData("attachment", savedFileName);
            }

            return new ResponseEntity<>(fileBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // 첨부파일 삭제(db에서 정상 삭제됨)
    @DeleteMapping(value = "/delete/{id}")
    public void deleteAttachment(@PathVariable Long id) {
        attachmentRepository.deleteById(id);
    }
}




//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.entity.Attachment;
//import com.groupware.wimir.service.AttachmentService;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/attachments")
//public class AttachmentController {
//    private final AttachmentService attachmentService;
//
//    public AttachmentController(AttachmentService attachmentService) {
//        this.attachmentService = attachmentService;
//    }
//
//    //null 값 방지 테스트용 코드
//    @PostMapping("/upload")
//    public ResponseEntity<String> uploadAttachment(@RequestParam("attachment") MultipartFile attachment,
//                                                   @RequestParam("documentId") Long documentId,
//                                                   Principal principal) {
//        // String writter = "작성자"; // 기존 코드 주석 처리
//        String writter = "작성자"; // 수정된 코드: 임의의 값을 writter 변수에 할당
//
//        try {
//            attachmentService.uploadAttachment(attachment, documentId, writter);
//            return ResponseEntity.ok("첨부파일이 업로드되었습니다.");
//        } catch (RuntimeException ex) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("첨부파일 업로드를 실패했습니다. 오류 : " + ex.getMessage());
//        }
//    }
//
////    @PostMapping("/upload")
////    public ResponseEntity<String> uploadAttachment(@RequestParam("attachment") MultipartFile attachment,
////                                                   @RequestParam("documentId") Long documentId,
////                                                   Principal principal) {
////        String currentUser = principal.getName();
////
////        try {
////            Long id = attachmentService.uploadAttachment(attachment, documentId, currentUser);
////
////            if (id != null) {
////                String response = "첨부파일이 업로드되었습니다. ID: " + id;
////                return ResponseEntity.ok(response);
////            } else {
////                String errorResponse = "첨부파일 업로드를 실패했습니다.";
////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
////            }
////        } catch (RuntimeException ex) {
////            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
////        }
////    }
//
//    @GetMapping("/download/{id}")
//    public ResponseEntity<Resource> downloadAttachment(@PathVariable Long id) {
//        byte[] fileData = attachmentService.downloadAttachment(id);
//        Attachment attachment = attachmentService.getAttachmentById(id);
//
//        ByteArrayResource resource = new ByteArrayResource(fileData);
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
//                .body(resource);
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<String> deleteAttachment(@PathVariable Long id, Principal principal) {
//        String currentUser = principal.getName();
//
//        try {
//            boolean isDeleted = attachmentService.deleteAttachment(id, currentUser);
//
//            if (isDeleted) {
//                return ResponseEntity.ok("첨부파일이 삭제되었습니다.");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("첨부파일 삭제를 실패했습니다.");
//            }
//        } catch (RuntimeException ex) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
//        }
//    }
//}
//package com.groupware.wimir;
//
//import com.groupware.wimir.controller.FileController;
//import com.groupware.wimir.service.FileService;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.mock.web.MockMultipartFile;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class FileTest {
//
//    @Test
//    void uploadFile_Success() {
//        // Mock 객체 생성
//        FileService fileService = mock(FileService.class);
//        FileController fileController = new FileController(fileService);
//
//        // 업로드할 파일과 문서 ID 생성
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
//        Long documentId = 1L;
//
//        // 파일 업로드 성공시 서비스 메서드의 반환값 설정
//        when(fileService.uploadFile(file, documentId)).thenReturn(true);
//
//        // 테스트 실행
//        ResponseEntity<String> response = fileController.uploadFile(file, documentId);
//
//        // 반환값 검증
//        assertNotNull(response);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals("첨부파일이 업로드되었습니다.", response.getBody());
//
//        // 서비스 메서드가 제대로 호출되었는지 검증
//        verify(fileService, times(1)).uploadFile(file, documentId);
//    }
//
//    @Test
//    void uploadFile_Failure() {
//        // Mock 객체 생성
//        FileService fileService = mock(FileService.class);
//        FileController fileController = new FileController(fileService);
//
//        // 업로드할 파일과 문서 ID 생성
//        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
//        Long documentId = 1L;
//
//        // 파일 업로드 실패시 서비스 메서드의 반환값 설정
//        when(fileService.uploadFile(file, documentId)).thenReturn(false);
//
//        // 테스트 실행
//        ResponseEntity<String> response = fileController.uploadFile(file, documentId);
//
//        // 반환값 검증
//        assertNotNull(response);
//        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
//        assertEquals("첨부파일 업로드를 실패했습니다.", response.getBody());
//
//        // 서비스 메서드가 제대로 호출되었는지 검증
//        verify(fileService, times(1)).uploadFile(file, documentId);
//    }
//}
//

package com.groupware.wimir;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.DocumentRepository;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class GroupwareApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private DocumentRepository documentRepository;

//	@Test
//	void convertAndSaveDocumentTest() throws IOException {
//		// Mock MultipartFile
//		MockMultipartFile file = new MockMultipartFile(
//				"file",
//				"test.txt",
//				"text/plain",
//				"Hello, World!".getBytes()
//		);
//
//		// 샘플 문서 생성
//		Document document = new Document();
//		document.setTitle("샘플 문서");
//
//		// 다운로드 폴더 경로 설정
//		String downloadFolderPath = System.getProperty("user.home") + "/Downloads/";
//
//		// 고유한 파일명 생성
//		String fileName = "document_" + System.currentTimeMillis();
//
//		// PDF 및 Excel 변환을 위한 파일 경로 설정
//		String pdfFilePath = downloadFolderPath + fileName + ".pdf";
//		String excelFilePath = downloadFolderPath + fileName + ".xlsx";
//
//		try {
//			// 파일 전송(Mock)
//			File uploadedFile = new File(downloadFolderPath + file.getOriginalFilename());
//			file.transferTo(uploadedFile);
//
//			// 문서를 Excel로 변환하여 저장
//			convertToExcel(uploadedFile, excelFilePath, document);
//
//			// Excel 파일이 생성되었는지 확인
//			File excelFile = new File(excelFilePath);
//			assertTrue(excelFile.exists());
//
//			System.out.println("Excel 파일이 성공적으로 생성되었습니다.");
//
//			// 필요한 추가 검증 또는 확인을 추가할 수 있습니다.
//
//		} catch (IOException ex) {
//			ex.printStackTrace();
//			// 예외 처리 또는 테스트 실패 처리를 수행할 수 있습니다.
//		}
//	}
//
//	private void convertToExcel(File uploadedFile, String excelFilePath, Document document) {
//		// Excel 변환 및 저장 로직을 구현해야 합니다.
//		// 주어진 파일(uploadedFile)과 문서(document)를 기반으로 Excel 파일을 생성하여 지정된 경로(excelFilePath)에 저장합니다.
//		// 실제로 Excel 변환 로직을 구현하셔야 합니다.
//	}
//
//

}

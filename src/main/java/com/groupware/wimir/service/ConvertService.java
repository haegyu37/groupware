package com.groupware.wimir.service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class ConvertService {

    private final ResourceLoader resourceLoader;

    public ConvertService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String convertDocument(MultipartFile file, String title, String path, String format) {
        String fileName = "document_" + UUID.randomUUID(); // 고유한 파일명 생성을 위해 UUID 사용
        String filePath = path + fileName + "." + format.toLowerCase();

        try {
            // 파일 저장
            File uploadedFile = new File(path + file.getOriginalFilename());
            file.transferTo(uploadedFile);

            // 파일 형식에 따른 저장
            if (format.equalsIgnoreCase("pdf")) {
                convertToPDF(uploadedFile, filePath, title);
            } else if (format.equalsIgnoreCase("excel")) {
                convertToExcel(uploadedFile, filePath, title);
            } else {
                // 지원하지 않는 형식인 경우 예외 처리
                throw new IllegalArgumentException("지원하지 않는 파일 형식입니다.");
            }

            // 변환 완료 후 다운로드 링크를 반환
            String downloadLink = getDownloadLink(filePath); // 다운로드 링크 생성 메서드 호출하여 반환

            // 임시 파일 삭제
            uploadedFile.delete();

            return downloadLink;

        } catch (IOException ex) {
            ex.printStackTrace();
            // 예외 처리 로직 추가
            throw new RuntimeException("파일 변환 및 저장 중 오류가 발생했습니다.");
        } catch (DocumentException e) {
            e.printStackTrace();
            // 예외 처리 로직 추가
            throw new RuntimeException("파일 변환 중 오류가 발생했습니다.");
        }
    }

    private void convertToExcel(File file, String excelFilePath, String document) throws IOException {
        Workbook workbook = new XSSFWorkbook(); // XLSX 파일 생성
        Sheet sheet = workbook.createSheet("Document"); // 엑셀 시트 생성

        Row row = sheet.createRow(0);   // 행 생성
        Cell cell = row.createCell(0);  // 셀 생성
        cell.setCellValue(document); // 데이터 쓰기

        try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
            workbook.write(fileOutputStream);
        }
    }

    private void convertToPDF(File file, String pdfFilePath, String document) throws DocumentException, IOException {
        com.itextpdf.text.Document pdfDocument = new com.itextpdf.text.Document();
        PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfFilePath));

        pdfDocument.open();
        pdfDocument.add(new Paragraph(document));
        pdfDocument.close();
    }

    private String getDownloadLink(String filePath) {
        Resource resource = resourceLoader.getResource("file:" + filePath);
        if (resource.exists()) {
            String fileName = resource.getFilename();

            // 파일의 다운로드 URL 생성
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/Downloads/")
                    .path(StringUtils.cleanPath(fileName))
                    .toUriString();

            return downloadUrl;
        }

        throw new RuntimeException("파일을 다운로드할 수 없습니다.");
    }
}

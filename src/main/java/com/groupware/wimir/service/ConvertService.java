//package com.groupware.wimir.service;
//
//import com.groupware.wimir.entity.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.PdfWriter;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@Service
//public class ConvertService {
//
//    public void convertAndSaveDocument(MultipartFile file, Document document) {
//        String downloadFolderPath = System.getProperty("user.home") + "/Downloads/";
//        String fileName = "document_" + System.currentTimeMillis(); // 예시: 문서별로 고유한 파일명 생성
//        String pdfFilePath = downloadFolderPath + fileName + ".pdf";
//        String excelFilePath = downloadFolderPath + fileName + ".xlsx";
//
//        try {
//            // 파일 저장
//            File uploadedFile = new File(downloadFolderPath + file.getOriginalFilename());
//            file.transferTo(uploadedFile);
//
//            // 파일 변환 및 저장
//            convertToPDF(uploadedFile, pdfFilePath, document);
//            convertToExcel(uploadedFile, excelFilePath, document);
//
//            // 변환 및 저장 완료 메시지 등 필요한 로직 추가
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            // 예외 처리 로직 추가
//        } catch (DocumentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void convertToExcel(File file, String excelFilePath, Document document) throws IOException {
//        Workbook workbook = new XSSFWorkbook(); // XLSX 파일 생성
//        Sheet sheet = workbook.createSheet("Document"); // 엑셀 시트 생성
//
//        Row row = sheet.createRow(0);   // 행 생성
//        Cell cell = row.createCell(0);  // 셀 생성
//        cell.setCellValue(document.getTitle()); // 데이터 쓰기
//
//        try (FileOutputStream fileOutputStream = new FileOutputStream(excelFilePath)) {
//            workbook.write(fileOutputStream);
//        }
//    }
//
//    private void convertToPDF(File file, String pdfFilePath, Document document) throws DocumentException, IOException {
//        com.itextpdf.text.Document pdfDocument = new com.itextpdf.text.Document();
//        PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfFilePath));
//
//        pdfDocument.open();
//        pdfDocument.add(new Paragraph(document.getContent()));
//        pdfDocument.close();
//    }
//}

//package com.groupware.ahnkookyukyu.service;
//
//import com.groupware.ahnkookyukyu.entity.Document;
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.PdfWriter;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//@Service
//public class FileConvertService {
//
//    public void convertToExcel(String excelFilePath, Document document) throws IOException {
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
//    public void convertToPDF(String pdfFilePath, Document document) throws DocumentException, IOException {
//        com.itextpdf.text.Document pdfDocument = new com.itextpdf.text.Document();
//        PdfWriter.getInstance(pdfDocument, new FileOutputStream(pdfFilePath));
//
//        pdfDocument.open();
//        pdfDocument.add(new Paragraph(document.getContent()));
//        pdfDocument.close();
//    }
//
////    public void uploadFile(MultipartFile file, Document document) {
////        // 파일 저장 경로 및 파일 이름 설정
////        String downloadFolderPath = System.getProperty("user.home") + "/Downloads/";
////        String fileName = "document_" + document.getUser().getId() + "_" + System.currentTimeMillis(); // 예시: 사용자 ID와 현재 시간을 조합하여 파일 이름 생성
////        String excelFilePath = downloadFolderPath + fileName + ".xlsx";
////        String pdfFilePath = downloadFolderPath + fileName + ".pdf";
////
////        // 파일 변환 로직
////    }
//
//}

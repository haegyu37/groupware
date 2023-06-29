package com.groupware.wimir.service;//package com.groupware.wimir.service;
//
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//
//public class HugasanApplicationFormExample {
//    public static void main(String[] args) {
//        try {
//            // 새로운 PDF 문서 생성
//            com.itextpdf.text.Document document = new com.itextpdf.text.Document();
//
//            // PDF Writer 생성
//            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:/Users/codepc/Downloads/hugasan_application.pdf"));
//
//            // 문서 열기
//            document.open();
//
//            // 폰트 설정
//            BaseFont baseFont = BaseFont.createFont("src/main/resources/font/Arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            Font font = new Font(baseFont, 12, Font.NORMAL);
//
//            // 문단 1: 제목
//            Paragraph titleParagraph = new Paragraph("휴가 신청서", font);
//            titleParagraph.setAlignment(com.itextpdf.text.Element.ALIGN_CENTER);
//            titleParagraph.setSpacingAfter(20);
//            document.add(titleParagraph);
//
//            // 문단 2: 신청자 정보
//            Paragraph applicantParagraph = new Paragraph("신청자: 홍길동", font);
//            document.add(applicantParagraph);
//
//            // 문단 3: 사유
//            Paragraph reasonParagraph = new Paragraph("신청 사유: 휴가", font);
//            document.add(reasonParagraph);
//
//            // 문단 4: 일자
//            Paragraph dateParagraph = new Paragraph("일자: 2023년 6월 1일부터 2023년 6월 10일까지", font);
//            document.add(dateParagraph);
//
//            // 문단 5: 승인자 목록
//            Paragraph approverListParagraph = new Paragraph("승인자 목록:", font);
//            document.add(approverListParagraph);
//
//            // 승인자 목록 추가
//            com.itextpdf.text.List approverList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
//            approverList.setIndentationLeft(20);
//            approverList.add(new com.itextpdf.text.ListItem("사장", font));
//            approverList.add(new com.itextpdf.text.ListItem("이사", font));
//            document.add(approverList);
//
//            // 문서 닫기
//            document.close();
//
//            System.out.println("휴가 신청서 생성 및 저장 완료");
//        } catch (IOException | DocumentException e) {
//            e.printStackTrace();
//        }
//    }
//}

import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class HugaEx {
    public static void main(String[] args) {
        try {
            // 새로운 워드 문서 생성
            XWPFDocument document = new XWPFDocument();

            // 제목
            XWPFParagraph titleParagraph = document.createParagraph();
            titleParagraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = titleParagraph.createRun();
            titleRun.setText("휴가 신청서");
            titleRun.setFontSize(20);
            titleRun.setBold(true);
            titleRun.addCarriageReturn();

            // 표 생성
            XWPFTable table = document.createTable(5, 2); // 4행 2열의 표 생성

            // 신청자 정보 셀
            XWPFTableCell applicantCell = table.getRow(0).getCell(0);
            applicantCell.setText("신청자 : ");
            table.getRow(0).getCell(1).setText("임성한");

            // 사유 셀
            XWPFTableCell reasonCell = table.getRow(1).getCell(0);
            reasonCell.setText("신청 사유 : ");
            table.getRow(1).getCell(1).setText("휴가");

            // 일자 셀
            XWPFTableCell dateCell = table.getRow(2).getCell(0);
            dateCell.setText("일자 : ");
            table.getRow(2).getCell(1).setText("2023년 6월 28일 ~ 2023년 6월 30일");

            // 승인자 목록 셀
            XWPFTableCell approverListCell = table.getRow(3).getCell(0);
            approverListCell.setText("");

            // 승인자 목록 행
            XWPFTableRow approverRow = table.createRow();
            table.removeRow(3); // 기존의 승인자 목록 셀을 포함한 행 삭제

            XWPFTableCell approverCell1 = approverRow.getCell(0);
            XWPFTableCell approverCell2 = approverRow.getCell(1);

            approverCell1.setText("사장");
            approverCell2.setText("이사");

            // 아래 행 비우기
            XWPFTableRow emptyRow = table.createRow();
            XWPFTableCell emptyCell1 = emptyRow.getCell(0);
            XWPFTableCell emptyCell2 = emptyRow.getCell(1);

            emptyCell1.setText("");
            emptyCell2.setText("");


            // 표 스타일 설정
            for (XWPFTableRow row : table.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
                }
            }

            // 문서 저장
            FileOutputStream outputStream = new FileOutputStream("C:/Users/codepc/Downloads/휴가신청서.docx");
            document.write(outputStream);
            outputStream.close();

            System.out.println("휴가 신청서 생성 및 저장 완료");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

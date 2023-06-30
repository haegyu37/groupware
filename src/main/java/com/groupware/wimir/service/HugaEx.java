package com.groupware.wimir.service;

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

package com.groupware.wimir.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class HtmlToPdf {

    public void convertHtmlToPdf(String htmlContent, String outputPath, String outputFileName) throws Exception {
        try (OutputStream os = new FileOutputStream(outputPath + File.separator + outputFileName + ".pdf")) {
            ITextRenderer renderer = new ITextRenderer();

            // 폰트 설정(한글 깨질시)
//            renderer.getFontResolver().addFont("path/to/font.ttf", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            renderer.setDocumentFromString(htmlContent);

            // A4 용지 크기 설정
            Document pdfDocument = new Document(PageSize.A4);
            PdfWriter writer = PdfWriter.getInstance(pdfDocument, os);
            pdfDocument.open();
            renderer.layout();
            renderer.createPDF(os);
            pdfDocument.close();
        }
    }
}




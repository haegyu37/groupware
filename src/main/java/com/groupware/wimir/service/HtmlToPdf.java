package com.groupware.wimir.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.FileOutputStream;
import java.io.OutputStream;

@Service
public class HtmlToPdf {

    public void convertHtmlToPdf(String htmlContent, String outputPath) throws Exception {
        try (OutputStream os = new FileOutputStream(outputPath)) {
            ITextRenderer renderer = new ITextRenderer();
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



//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.service.HtmlToPdf;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.File;
//
//@RestController
//@RequestMapping
//public class PdfController {
//
//    private final HtmlToPdf htmlToPdf;
//
//    public PdfController(HtmlToPdf htmlToPdf) {
//        this.htmlToPdf = htmlToPdf;
//    }
//
//    @PostMapping(value = "/pdf")
//    public ModelAndView convertToPdf(@RequestParam("htmlContent") String htmlContent,
//                                     @RequestParam("outputFileName") String outputFileName) {
//        try {
//            // 경로 생성(만약 해당 경로에 폴더가 없는 경우)
//            File directory = new File("C:\\Users\\codepc\\Downloads");
//            if (!directory.exists()) {
//                directory.mkdirs();
//            }
//
//            // pdf 저장
//            String outputPath = "C:\\Users\\codepc\\Downloads";
//            htmlToPdf.convertHtmlToPdf(htmlContent, outputPath, outputFileName);
//
//            ModelAndView modelAndView = new ModelAndView("result");
//            modelAndView.addObject("message", "HTML을 PDF로 변환했습니다.");
//            return modelAndView;
//        } catch (Exception e) {
//            ModelAndView modelAndView = new ModelAndView("result");
//            modelAndView.addObject("error", "PDF 변환 중 오류가 발생했습니다.");
//            return modelAndView;
//        }
//    }
//}
//
//

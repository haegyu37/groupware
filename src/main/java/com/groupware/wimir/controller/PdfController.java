package com.groupware.wimir.controller;

import com.groupware.wimir.service.HtmlToPdf;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/convert")
public class PdfController {

    private final HtmlToPdf htmlToPdf;

    public PdfController(HtmlToPdf htmlToPdf) {
        this.htmlToPdf = htmlToPdf;
    }

    @PostMapping("/pdf")
    public ModelAndView convertToPdf(@RequestParam("htmlContent") String htmlContent) {
        try {
            htmlToPdf.convertHtmlToPdf(htmlContent, "output.pdf");

            ModelAndView modelAndView = new ModelAndView("result");
            modelAndView.addObject("message", "HTML을 PDF로 변환했습니다.");
            return modelAndView;
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("result");
            modelAndView.addObject("error", "PDF 변환 중 오류가 발생했습니다.");
            return modelAndView;
        }
    }
}


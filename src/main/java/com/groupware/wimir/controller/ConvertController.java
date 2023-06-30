package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.service.ConvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ConvertController {

    private final ConvertService convertService;

    @Autowired
    public ConvertController(ConvertService convertService) {
        this.convertService = convertService;
    }

    @GetMapping("/convert")
    public String showConvertForm() {
        return "convert-form"; // 변환을 요청할 수 있는 폼 페이지를 반환
    }

    @PostMapping("/convert/pdf")
    public String convertToPDF(@RequestParam("file") MultipartFile file,
                               @RequestParam("title") String title,
                               @RequestParam("path") String path,
                               Model model) {
        String downloadLink = convertService.convertDocument(file, title, path, "pdf");
        model.addAttribute("downloadLink", downloadLink);
        return "pdf-download"; // PDF 변환 후 다운로드 링크를 보여주는 페이지를 반환
    }

    @PostMapping("/convert/excel")
    public String convertToExcel(@RequestParam("file") MultipartFile file,
                                 @RequestParam("title") String title,
                                 @RequestParam("path") String path,
                                 Model model) {
        String downloadLink = convertService.convertDocument(file, title, path,"excel");
        model.addAttribute("downloadLink", downloadLink);
        return "excel-download"; // 엑셀 변환 후 다운로드 링크를 보여주는 페이지를 반환
    }
}

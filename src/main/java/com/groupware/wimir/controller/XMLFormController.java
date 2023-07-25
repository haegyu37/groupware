package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Template;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class XMLFormController {

    // 파일 저장 경로 설정
    private static final String UPLOAD_FOLDER = "xml/";

    // 파일 저장 로직 추가
    private void saveFile(String fileName, String content) throws IOException {
        Path path = Paths.get(UPLOAD_FOLDER + fileName);
        Files.write(path, content.getBytes());
    }

    // DB에 저장 로직 추가
    @Autowired
    private TemplateService templateService;

    private void saveToDatabase(String name, String title, String content, String category) {
        Template template = new Template();
        template.setName(name);
        template.setTitle(title);
        template.setContent(content);
        template.setCategory(category);
        templateService.save(template);
    }

    @GetMapping("/")
    public ResponseEntity<String> showForm() {
        // 기본 템플릿 제공
        String defaultXML = "<root>\n\t<element1>Value 1</element1>\n\t<element2>Value 2</element2>\n</root>";
        return ResponseEntity.ok(defaultXML);
    }

    @PostMapping("/save")
    public String saveXML(@RequestParam("name") String name,
                          @RequestParam("title") String title,
                          @RequestParam("category") String category,
                          @RequestBody String xmlContent,
                          RedirectAttributes redirectAttributes) {
        try {
            saveFile(name, xmlContent); // 파일 저장 로직 호출
            saveToDatabase(name, title, xmlContent, category); // DB에 저장 로직 호출
        } catch (IOException e) {
            e.printStackTrace();
            return "Error"; // 오류 처리
        }
        redirectAttributes.addFlashAttribute("xmlContent", xmlContent);
        return "redirect:/";
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadXML(@RequestParam("xmlFile") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            // 업로드한 파일의 내용을 반환합니다.
            return ResponseEntity.ok(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("XML 파일을 업로드를 실패했습니다.");
        }
    }
}
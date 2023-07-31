package com.groupware.wimir.controller;

import ch.qos.logback.classic.Logger;
import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupware.wimir.entity.Template;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    // 템플릿 생성
    @PostMapping("/create")
    public ResponseEntity<String> createTemplate(@RequestBody TemplateDTO templateDTO) {
        try {
            // 필수 필드인지 확인하고 유효성 검사
            String title = templateDTO.getTitle();
            String content = templateDTO.getContent();
            String category = templateDTO.getCategory();

            if (title == null || content == null || category == null) {
                return ResponseEntity.badRequest().body("제목, 내용, 카테고리는 필수 필드입니다.");
            }

            // db에 양식 데이터에 저장
            Template template = Template.builder()
                    .title(title)
                    .content(content)
                    .category(category)
                    .build();
            templateService.createTemplate(template);

            // html 파일로 저장
            File file = new File("c://templates/" + title + ".html");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

            return ResponseEntity.ok("양식 등록을 완료했습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 오류가 발생하면 콘솔에 간단한 오류 메시지를 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("양식 등록을 실패했습니다.");
        }
    }

    // 템플릿 수정
    @PutMapping("/update/{id}")
    public String updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        // db에 양식 데이터 수정
        templateService.updateTemplate(id, templateDTO);

        // 수정된 파일을 기존 파일에 덮어쓰기 해서 저장(오류있음. 덮어쓰기가 아니라 새 파일 생성)
        Template template = templateService.getTemplateById(id);
        String fileName = template.getTitle() + ".html";
        File file = new File("c://templates/" + fileName);
        try (OutputStream outputStream = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            writer.write(template.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    // 템플릿 삭제
    @DeleteMapping("/delete/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        // db에 양식 데이터 삭제
        Template template = templateService.getTemplateById(id);

        templateService.deleteTemplate(id);

        // 저장된 양식 html 파일은 삭제되지 않고 아카이브 폴더로 이동
        File file = new File(template.getTitle() + ".html");
        File archiveDir = new File("c://templates/archive/");
        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
        }
        file.renameTo(new File(archiveDir, file.getName()));

        return "redirect:/";
    }

    // 템플릿 조회
    @GetMapping("/get/{id}")
    public TemplateDTO getTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);

        TemplateDTO templateDTO = new TemplateDTO(
                template.getId(),
                template.getTitle(),
                template.getContent(),
                template.getCategory()
        );
        return templateDTO;
    }

    // 템플릿 목록
    @GetMapping("/list")
    public ResponseEntity<List<Template>> getTemplatesList() {
        try {
            List<Template> templates = templateService.getAllTemplates();
            return ResponseEntity.ok(templates);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
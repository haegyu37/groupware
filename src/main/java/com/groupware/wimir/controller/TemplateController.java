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
    @PostMapping(value = "/create")
    public ResponseEntity<String> createTemplate(@RequestBody TemplateDTO templateDTO) {
        try {
            // 필수 필드인지 확인하고 유효성 검사
            String category = templateDTO.getCategory();
            String content = templateDTO.getContent();

            if (category == null || content == null) {
                return ResponseEntity.badRequest().body("제목, 내용, 카테고리는 필수 필드입니다.");
            }

            // db에 양식 데이터에 저장
            Template template = Template.builder()
                    .category(category)
                    .content(content)
                    .build();
            templateService.createTemplate(template);

            // html 파일로 저장
            File file = new File("c://templates/" + category + ".html");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            }

            return ResponseEntity.ok("양식 등록을 완료했습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 오류가 발생하면 메시지를 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("양식 등록을 실패했습니다.");
        }
    }

    // 템플릿 수정
    @PutMapping(value = "/update/{id}")
    public String updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        // db에 양식 데이터 수정
        templateService.updateTemplate(id, templateDTO);

        // 수정된 파일을 기존 파일에 덮어쓰기 해서 저장(오류있음. 덮어쓰기가 아니라 새 파일 생성)
        Template template = templateService.getTemplateById(id);
        String fileName = template.getCategory() + ".html";
        File file = new File("c://templates/" + fileName);
        try (OutputStream outputStream = new FileOutputStream(file);
             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
            writer.write(template.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 수정된 글의 상세 조회 페이지로 리다이렉트
        return "redirect:/get/" + id;
    }

    // 템플릿 삭제
    @DeleteMapping(value = "/delete/{id}")
    public String deleteTemplate(@PathVariable Long id) {
        // db에 양식 데이터 삭제
        Template template = templateService.getTemplateById(id);

        templateService.deleteTemplate(id);

        // 저장된 양식 html 파일은 삭제되지 않고 아카이브 폴더로 이동(현재 폴더로 이동되지 않음)
        File file = new File(template.getCategory() + ".html");
        File archiveDir = new File("c://templates/archive/");
        if (!archiveDir.exists()) {
            archiveDir.mkdirs();
        }
        file.renameTo(new File(archiveDir, file.getName()));

        return "redirect:/list";
    }

    // 템플릿 조회
    @GetMapping(value = "/read/{id}")
    public TemplateDTO readTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);

        TemplateDTO templateDTO = new TemplateDTO(
                template.getId(),
                template.getCategory(),
                template.getContent()
        );
        return templateDTO;
    }

    // 템플릿 목록
    @GetMapping(value = "/list")
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
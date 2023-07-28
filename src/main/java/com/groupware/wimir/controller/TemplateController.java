package com.groupware.wimir.controller;

import ch.qos.logback.classic.Logger;
import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupware.wimir.entity.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



@RestController
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;
    private Logger logger;

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    // 템플릿 생성
    @PostMapping("/create")
    public ResponseEntity<String> createTemplate(@RequestBody TemplateDTO templateDTO) {
        try {
            // db에 양식 데이터에 저장
            Template template = Template.builder()
                    .title(templateDTO.getTitle())
                    .content(templateDTO.getContent())
                    .category(templateDTO.getCategory())
                    .build();
            templateService.createTemplate(template);

            // html 파일로 저장
            File file = new File("c://templates/" + template.getTitle() + ".html");
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(template.getContent());
            }
            
            return ResponseEntity.ok("양식 등록을 완료했습니다.");
        } catch (Exception e) {
            logger.error("양식 등록을 실패했습니다.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("양식 등록을 실패했습니다.");
        }
    }

    // 템플릿 수정
    @PutMapping("/update/{id}")
    public String updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        // db에 양식 데이터 수정
        templateService.updateTemplate(id, templateDTO);

        // 수정된 파일을 기존 파일에 덮어쓰기 해서 저장
        Template template = templateService.getTemplateById(id);
        try {
            File file = new File(template.getTitle() + ".html");
            FileWriter writer = new FileWriter(file);
            writer.write(template.getContent());
            writer.close();
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
    public List<Template> getTemplatesList() {
        return templateService.getTemplatesList();
    }
}
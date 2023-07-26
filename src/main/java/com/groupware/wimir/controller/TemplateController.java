package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    // 템플릿 생성
    @PostMapping("/create")
    public String createTemplate(@RequestBody TemplateDTO templateDTO) throws IOException {
        // Template 엔티티로 변환하여 저장
        Template template = Template.builder()
                .name(templateDTO.getName())
                .title(templateDTO.getTitle())
                .content(templateDTO.getContent())
                .category(templateDTO.getCategory())
                .data(templateDTO.getData())
                .build();
        templateService.createTemplate(template);

        // 모든 내용이 HTML 파일로 저장(pc에도 저장)
        try {
            File file = new File("c://templates/" + template.getTitle() + ".html");
            FileWriter writer = new FileWriter(file);
            writer.write(template.getContent());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/";    // 작성 누르기 전 페이지 주소 넣음
    }

    // 템플릿 조회
    @GetMapping("/get/{id}")
    public TemplateDTO getTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);
        // Template 엔티티를 TemplateDTO로 변환하여 반환
        TemplateDTO templateDTO = new TemplateDTO(
                template.getId(),
                template.getName(),
                template.getTitle(),
                template.getContent(),
                template.getCategory(),
                template.getData()
        );
        return templateDTO;
    }

    // 템플릿 수정
    @PutMapping("/update/{id}")
    public String updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        // 템플릿 업데이트
        templateService.updateTemplate(id, templateDTO);

        // HTML 파일로 저장
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
        // 템플릿 삭제
        Template template = templateService.getTemplateById(id);
        templateService.deleteTemplate(id);
        // HTML 파일 삭제
        File file = new File(template.getTitle() + ".html");
        file.delete();
        return "redirect:/";
    }

    // 템플릿 목록
    @GetMapping("/list")
    public List<Template> getTemplatesList() {
        return templateService.getTemplatesList();
    }
}
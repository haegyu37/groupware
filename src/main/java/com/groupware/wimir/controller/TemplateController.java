package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.groupware.wimir.entity.Template;

import java.util.List;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private TemplateRepository templateRepository;

    // 템플릿 생성 -> 관리자
    @PostMapping(value = "/create")
    public Template createTemplate(@RequestBody TemplateDTO templateDTO) {
        Template template = new Template();
        template.setCategory(templateDTO.getCategory());
        template.setContent(templateDTO.getContent());
        return templateRepository.save(template);
    }

    // 템플릿 수정 -> 관리자
    @PutMapping(value = "/update/{id}")
    public Template updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        Template updateTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("양식을 찾을 수 없습니다. : " + id));

        if (!updateTemplate.isActive()) {
            throw new IllegalArgumentException("해당 양식은 사용할 수 없습니다.");
        }
        updateTemplate.setCategory(templateDTO.getCategory());
        updateTemplate.setContent(templateDTO.getContent());
        return templateRepository.save(updateTemplate);
    }

    // 템플릿 삭제 -> 관리자
    @DeleteMapping(value = "/delete/{id}")
    public void deleteTemplate(@PathVariable Long id) {
        Template template = templateRepository.findById(id).orElse(null);

        if (template != null) {
            template.setActive(false); // 비활성화
            templateRepository.save(template);
        }
    }

    // 템플릿 조회
    @GetMapping(value = "/{id}")
    public Template readTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);
        if (!template.isActive()) {
            throw new ResourceNotFoundException("템플릿을 찾을 수 없습니다. : " + id);
        }
        return template;
    }

    // 템플릿 목록
    @GetMapping(value = "/list")
    public List<Template> getTemplatesList() {
        List<Template> activeTemplates = templateRepository.findByActiveTrue();
        return activeTemplates;
    }
}
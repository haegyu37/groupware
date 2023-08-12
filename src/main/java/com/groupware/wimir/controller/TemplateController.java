package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.groupware.wimir.entity.Template;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private TemplateRepository templateRepository;

//    // 템플릿 생성 -> 관리자
//    @PostMapping(value = "/create")
//    public Template createTemplate(@RequestBody TemplateDTO templateDTO) {
//        Template template = new Template();
//        template.setCategory(templateDTO.getCategory());
//        template.setContent(templateDTO.getContent());
//        return templateRepository.save(template);
//    }
//
//    // 템플릿 수정 -> 관리자
//    @PutMapping(value = "/update/{id}")
//    public Template updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
//        Template updateTemplate = templateRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("문서를 찾을 수 없습니다. : " + id));
//        updateTemplate.setCategory(templateDTO.getCategory());
//        updateTemplate.setCategory(templateDTO.getContent());
//
//        return templateRepository.save(updateTemplate);
//    }
//
//    // 템플릿 삭제 -> 관리자
//    @DeleteMapping(value = "/delete/{id}")
//    public void deleteTemplate(@PathVariable Long id) {
//        templateRepository.deleteById(id);
//    }
//
    // 템플릿 조회
    @GetMapping(value = "/{id}")
    public Template readTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);
        return template;
    }

    // 템플릿 목록
    @GetMapping(value = "/list")
    public List<Template> getTemplatesList() {
        List<Template> templates = templateRepository.findAll();
        return templates;
    }

}
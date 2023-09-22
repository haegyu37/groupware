package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.groupware.wimir.entity.Template;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    @Autowired
    private TemplateService templateService;
    @Autowired
    private TemplateRepository templateRepository;

    // 템플릿 조회
    @GetMapping(value = "/{id}")
    public Template readTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);
        return template;
    }

    //템플릿 목록
    @GetMapping(value = "/list")
    public List<Template> getTemplatesList() {
        List<Template> allTemplates = templateRepository.findAll();
        System.out.println("양식 다" + allTemplates);

        List<Template> temNotDelete = allTemplates.stream()
                .filter(template -> template.getStatus() != null && !template.getStatus().equals("delete"))
                .collect(Collectors.toList());

        return temNotDelete;
    }

}
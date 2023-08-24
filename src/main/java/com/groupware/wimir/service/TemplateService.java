package com.groupware.wimir.service;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.TemplateRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    private final TemplateRepository templateRepository;
    private List<Template> templatesList;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Template createTemplate(Template template) {
        return templateRepository.save(template);
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("양식을 찾을 수 없습니다 : " + id));
    }


}
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

//    public Template createTemplate(Template template) {
//        return templateRepository.save(template);
//    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("양식을 찾을 수 없습니다 : " + id));
    }

    public void save(Template template) {
        templateRepository.save(template);
    }

    public Template updateTemplate(Long id, TemplateDTO templateDTO) {
        Template template = templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("양식을 찾을 수 없습니다 : " + id));

        // category과 content 업데이트
        if (templateDTO.getCategory() != null) {
            template.setCategory(templateDTO.getCategory());
        }
        if (templateDTO.getContent() != null) {
            template.setContent(templateDTO.getContent());
        }

        return templateRepository.save(template);
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    public List<Template> getTemplatesList() {
        return templateRepository.findAll();
    }

    public void setTemplatesList(List<Template> templatesList) {
        this.templatesList = templatesList;
    }
}

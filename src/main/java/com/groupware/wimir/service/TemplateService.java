package com.groupware.wimir.service;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemplateService {
    @Autowired
    private TemplateRepository templateRepository;

    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("양식을 찾을 수 없습니다 : " + id));
    }


}
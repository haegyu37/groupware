package com.groupware.wimir.controller;

import ch.qos.logback.classic.Logger;
import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.TemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.groupware.wimir.entity.Template;

import java.io.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor

public class TemplateController {

    private final TemplateService templateService;
    private final TemplateRepository templateRepository;

    // 템플릿 생성
    @PostMapping(value = "/create")
    public ResponseEntity<String> createTemplate(@RequestBody TemplateDTO templateDTO) {
        return templateService.createTemplate(templateDTO);
    }

    // 템플릿 수정
    @PutMapping(value = "/update/{id}")
    public Template updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        return templateService.updateTemplate(id, templateDTO);
    }

    // 템플릿 삭제
    @DeleteMapping(value = "/delete/{id}")
    public void deleteTemplate(@PathVariable Long id) {
        templateRepository.deleteById(id);
    }

    // 템플릿 조회
    @GetMapping(value = "/{id}")
    public Optional<Template> readTemplate(@PathVariable Long id) {
        Optional<Template> template = templateRepository.findById(id);
        return template;
    }

    // 템플릿 목록
    @GetMapping(value = "/list")
    public List<Template> getTemplatesList() {
        List<Template> templates = templateRepository.findAll();
        return templates;
    }

}
package com.groupware.ahnkookyukyu.controller;

import com.groupware.ahnkookyukyu.entity.Template;
import com.groupware.ahnkookyukyu.service.TemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/templates")
public class TemplateController {
    private final TemplateService templateService;

    public TemplateController(TemplateService templateService) {
        this.templateService = templateService;
    }

    @PostMapping
    public ResponseEntity<Template> createTemplate(@RequestBody Template template) {
        Template savedTemplate = templateService.saveTemplate(template);
        return ResponseEntity.ok(savedTemplate);
    }

    @GetMapping
    public ResponseEntity<List<Template>> getAllTemplates() {
        List<Template> templates = templateService.getAllTemplates();
        return ResponseEntity.ok(templates);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<Template> getTemplateById(@PathVariable Long id) {
//        Template template = templateService.getTemplateById(id);
//        return ResponseEntity.ok(template);
//    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id) {
        templateService.deleteTemplate(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/{templateId}/generate")
//    public ResponseEntity<String> generateDocument(@PathVariable Long templateId, @RequestBody Map<String, Object> data,
//                                                   @RequestParam("outputPath") String outputPath) {
//        templateService.generateDocument(templateId, data, outputPath);
//        return ResponseEntity.ok("Document generated successfully");
//    }
}



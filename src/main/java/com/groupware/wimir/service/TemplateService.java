package com.groupware.wimir.service;

import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.TemplateRepository;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class TemplateService {
    private final TemplateRepository templateRepository;

    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public Template saveTemplate(Template template) {
        return templateRepository.save(template);
    }

    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }

    public void deleteTemplate(Long id) {
        templateRepository.deleteById(id);
    }

    public void generateDocument(Long templateId, Map<String, Object> data, String outputPath) {
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + templateId));

        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath)) {
            XWPFDocument document = new XWPFDocument();

            // 양식에 데이터 적용 및 문서 생성 로직 작성
            // 데이터를 양식에 적용하여 문서를 생성하는 코드를 작성합니다.
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText("Example Data: " + data.toString());

            document.write(fileOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
            // 예외 처리
            throw new RuntimeException("Failed to generate the document: " + e.getMessage());
        }
    }

    public Template getTemplateById(Long id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found with ID: " + id));
    }

    public void save(Template template) {
        templateRepository.save(template);
    }
}

//package com.groupware.wimir.service;
//
//import com.groupware.wimir.entity.Template;
//import com.groupware.wimir.repository.TemplateRepository;
//import org.apache.poi.xwpf.usermodel.XWPFDocument;
//import org.apache.poi.xwpf.usermodel.XWPFParagraph;
//import org.apache.poi.xwpf.usermodel.XWPFRun;
//import org.springframework.data.crossstore.ChangeSetPersister;
//import org.springframework.stereotype.Service;
//
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
//@Service
//public class TemplateService {
//    private final TemplateRepository templateRepository;
//
//    public TemplateService(TemplateRepository templateRepository) {
//        this.templateRepository = templateRepository;
//    }
//
//    public Template saveTemplate(Template template) {
//        return templateRepository.save(template);
//    }
//
//    public List<Template> getAllTemplates() {
//        return templateRepository.findAll();
//    }
//
//    public void deleteTemplate(Long id) {
//        templateRepository.deleteById(id);
//    }
//
//    public void generateDocument(Long templateId, Map<String, Object> data, String outputPath) {
//        try {
//            Template template = templateRepository.findById(templateId)
//                    .orElseThrow(() -> new ChangeSetPersister.NotFoundException());
//        } catch (ChangeSetPersister.NotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try (FileOutputStream fileOutputStream = new FileOutputStream(outputPath)) {
//            XWPFDocument document = new XWPFDocument();
//
//            // 양식에 데이터 적용 및 문서 생성 로직 작성
//            // 데이터를 양식에 적용하여 문서를 생성하는 코드를 작성합니다.
//
//            // 예시로 데이터를 특정 위치에 삽입하는 코드를 작성합니다.
//            XWPFParagraph paragraph = document.createParagraph();
//            XWPFRun run = paragraph.createRun();
//            run.setText("Example Data: " + data.toString());
//
//            document.write(fileOutputStream);
//        } catch (IOException e) {
//            e.printStackTrace();
//            // 예외 처리
//
//        }
//    }
//
//    public Template getTemplateById(Long id) {
//        return null;
//    }
//}

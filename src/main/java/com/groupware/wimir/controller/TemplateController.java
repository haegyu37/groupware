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

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor

public class TemplateController {

    private final TemplateService templateService;
    private final TemplateRepository templateRepository;
//    @Autowired
//    public TemplateController(TemplateService templateService, TemplateRepository templateRepository) {
//        this.templateService = templateService;
//        this.templateRepository =
//    }

    // 템플릿 생성
    @PostMapping(value = "/create")
    public ResponseEntity<String> createTemplate(@RequestBody TemplateDTO templateDTO) {
        try {
            String category = templateDTO.getCategory();
            String content = templateDTO.getContent();

            //제목 또는 내용이 null값이면 알림
            if (category == null || content == null) {
                return ResponseEntity.badRequest().body("제목, 내용은 필수입니다.");
            }

//            // db에 양식 데이터에 저장
//            Template template = Template.builder()
//                    .category(category)
//                    .content(content)
//                    .build();
            Template template = new Template();
            template.setCategory(category);
            template.setContent(content);

            templateRepository.save(template);
//            templateService.createTemplate(template);

//            // html 파일로 저장
//            File file = new File("c://templates/" + category + ".html"); //그냥 c 드라이브에 저장하면 안되묘??
//            try (FileWriter writer = new FileWriter(file)) {
//                writer.write(content);
//            }

            return ResponseEntity.ok("양식 등록을 완료했습니다.");
        } catch (Exception e) {
            //에러 메세지
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("양식 등록을 실패했습니다.");
        }
    }

//    @PostMapping(value = "/create")
//    public ResponseEntity<String> createTemplate(@RequestBody TemplateDTO templateDTO) {
//        try {
//            String category = templateDTO.getCategory();
//            String content = templateDTO.getContent();
//
//            if (category == null || content == null) {
//                return ResponseEntity.badRequest().body("제목, 내용, 카테고리는 필수입니다.");
//            }
//
//            // db에 양식 데이터에 저장
//            Template template = Template.builder()
//                    .category(category)
//                    .content(content)
//                    .build();
//            templateService.createTemplate(template);
//
//            return ResponseEntity.ok("양식 등록을 완료했습니다.");
//        } catch (Exception e) {
//            e.printStackTrace(); // 오류가 발생하면 메시지를 출력
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("양식 등록을 실패했습니다.");
//        }
//    }

    // 템플릿 수정
    @PutMapping(value = "/update/{id}")
    public Template updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
        // db에 양식 데이터 수정
//        templateService.updateTemplate(id, templateDTO);

        // 수정된 파일을 새로 저장
//        Template template = templateService.getTemplateById(id);
//        String fileName = template.getCategory() + ".html";
//        File file = new File("c://templates/" + fileName);
//        try (OutputStream outputStream = new FileOutputStream(file);
//             OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
//            writer.write(template.getContent());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // 수정된 글의 상세 조회 페이지로 리다이렉트
        return templateService.updateTemplate(id, templateDTO);
    }

//    @PutMapping(value = "/update/{id}")
//    public String updateTemplate(@PathVariable Long id, @RequestBody TemplateDTO templateDTO) {
//        // db에 양식 데이터 수정
//        templateService.updateTemplate(id, templateDTO);
//
//        // 수정된 글의 상세 조회 페이지로 리다이렉트
//        return "redirect:/read/" + id;
//    }

    // 템플릿 삭제
    @DeleteMapping(value = "/delete/{id}")
    public void deleteTemplate(@PathVariable Long id) {
//        // db에 양식 데이터 삭제
//        Template template = templateService.getTemplateById(id);
//
//        templateService.deleteTemplate(id);

        templateRepository.deleteById(id);

//        // 저장된 양식 html 파일은 삭제되지 않고 아카이브 폴더로 이동(현재 폴더로 이동되지 않음)
//        File file = new File(template.getCategory() + ".html");
//        File archiveDir = new File("c://templates/archive/");
//        if (!archiveDir.exists()) {
//            archiveDir.mkdirs();
//        }
//        file.renameTo(new File(archiveDir, file.getName()));

//        return "redirect:/list";
    }

//    @DeleteMapping(value = "/delete/{id}")
//    public String deleteTemplate(@PathVariable Long id) {
//        // db에 양식 데이터 삭제
//        templateService.deleteTemplate(id);
//
//        return "redirect:/list";
//    }

    // 템플릿 조회
    @GetMapping(value = "/{id}")
    public Template readTemplate(@PathVariable Long id) {
        Template template = templateService.getTemplateById(id);

//        TemplateDTO templateDTO = new TemplateDTO(
////                template.getId(),
//                template.getCategory(),
//                template.getContent()
//        );
        return template;
    }

    // 템플릿 목록
    @GetMapping(value = "/list")
    public List<Template> getTemplatesList() {
//        try {
             List<Template> templates = templateRepository.findAll();
             return templates;
//            return ResponseEntity.ok(templates);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
    }

}
package com.groupware.wimir.service;

import com.groupware.wimir.DTO.TemplateDTO;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TemplateService {
    private final TemplateRepository templateRepository;

    //템플릿 생성
    public ResponseEntity<String> createTemplate(TemplateDTO templateDTO) {
        try {
            String category = templateDTO.getCategory();
            String content = templateDTO.getContent();

            // 제목 또는 내용이 null값이면 알림
            if (category == null || content == null) {
                return ResponseEntity.badRequest().body("제목, 내용은 필수입니다.");
            }

            Template template = new Template();
            template.setCategory(category);
            template.setContent(content);

            templateRepository.save(template);

            return ResponseEntity.ok("양식 등록을 완료했습니다.");
        } catch (Exception e) {
            // 에러 메세지
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("양식 등록을 실패했습니다.");
        }
    }

    //템플릿 수정
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
}

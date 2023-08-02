package com.groupware.wimir.DTO;

import lombok.*;

import java.util.Map;

//@AllArgsConstructor
@ToString
@Getter
@Setter
public class TemplateDTO {
    private Long id;
    private String category;
    private String content;

    // 기본 생성자
    public TemplateDTO() {
    }

    // 모든 필드를 받는 생성자
    public TemplateDTO(Long id, String category, String content) {
        this.id = id;
        this.category = category;
        this.content = content;
    }
}


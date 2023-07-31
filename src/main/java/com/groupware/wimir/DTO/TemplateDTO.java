package com.groupware.wimir.DTO;

import lombok.*;

import java.util.Map;

//@AllArgsConstructor
@ToString
@Getter
@Setter
public class TemplateDTO {
    private Long id;
    private String title;
    private String content;
    private String category;

    // 기본 생성자
    public TemplateDTO() {
    }

    // 모든 필드를 받는 생성자
    public TemplateDTO(Long id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }
}


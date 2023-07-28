package com.groupware.wimir.DTO;

import lombok.*;

import java.util.Map;

//@AllArgsConstructor
@ToString
@Getter
@Setter
public class TemplateDTO {
    private Long id; //양식 아이디

    private String title; //양식 제목

    private String content; //양식 내용

    private String category; //양식명

    private Map<String, String> data; // 추가 데이터

    public TemplateDTO(Long id, String title, String content, String category) {
    }
}

package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //양식 아이디

    private String title; //양식 제목

    @Column(columnDefinition = "TEXT")
    private String content; //양식 내용

    private String category; //양식명


    @Builder
    public Template(Long id, String title, String content, String category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

}


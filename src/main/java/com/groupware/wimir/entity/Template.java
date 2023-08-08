package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //양식 아이디

    private String category; //양식명

    @Column(columnDefinition = "TEXT")
    private String content; //양식 내용

    @Builder
    public Template(Long id, String category, String content) {
        this.id = id;
        this.category = category;
        this.content = content;
    }

}


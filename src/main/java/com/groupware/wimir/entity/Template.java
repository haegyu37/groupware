package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;


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

    private boolean active = true; // 활성/비활성 상태

    @Builder
    public Template(Long id, String category, String content, boolean active) {
        this.id = id;
        this.category = category;
        this.content = content;
        this.active = active;
    }

}


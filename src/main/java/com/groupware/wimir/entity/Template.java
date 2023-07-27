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

    private String content; //양식 내용

    private String category; //양식명

    @ElementCollection
    private Map<String, String> data = new HashMap<>(); // 추가 데이터

    @Builder
    public Template(Long id, String title, String content, String category, Map<String, String> data) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
        this.data = data;
    }

    // 데이터 맵에 데이터 추가하는 메소드
    public void addData(String key, String value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    // 데이터 맵에서 데이터 조회하는 메소드
    public String getData(String key) {
        if (data == null) {
            return null;
        }
        return data.get(key);
    }

}


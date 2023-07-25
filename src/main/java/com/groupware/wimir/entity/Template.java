package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //양식 아이디

    private String name; // 양식명

    private String title; //양식 제목

    private String content; //양식 내용

    private String category; //양식 카테고리



}

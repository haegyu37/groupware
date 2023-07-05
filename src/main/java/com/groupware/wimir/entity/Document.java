package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "doc")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //문서 아이디

    @Column(name = "title")
    private String title; //문서 제목

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; //문서 내용

//    @Column(name = "writer")
//    private String writer; //문서 작성자

    @Column(name = "written_date")
    private LocalDateTime writtenDate; //문서 작성일

    @OneToOne
    @JoinColumn(name = "tem_id")
    private Template tem; //문서 양식

    @OneToOne
    @JoinColumn(name="app_id")
    private App app; //결재 아이디

    @OneToOne
    @JoinColumn(name="users_id")
    private Users users; //직원(작성자) 아이디


}

package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Column(name = "written_date")
    private LocalDateTime writtenDate; //문서 작성일

    @OneToOne
    @JoinColumn(name = "tem_id")
    private Template tem; //문서 양식

    @OneToOne
    @JoinColumn(name="app_id")
    private App app; //결재 아이디

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member; //직원(작성자) 아이디

    @OneToOne
    @JoinColumn(name = "line_id")
    private Line line; //결재라인 아이디


}

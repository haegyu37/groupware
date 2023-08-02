package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "document")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //문서 Id

    private String title; //문서명

    @Column(columnDefinition = "TEXT")
    private String content; //문서내용

    private LocalDateTime createDate; //작성일

    private LocalDateTime updateDate; //수정일

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer; // 작성자 (Member와 연관관계)

    @ManyToOne
    @JoinColumn(name = "tem_id")
    private Template template; // 양식명

    private int status; // 1: 작성 상태, 0: 임시저장 상태

    private Long dno = 0L;  // 문서 번호(디폴트 값은 0)

    private Long sno = 0L; // 문서 임시저장 번호(디폴트 값은 0)

    private String result; //결재 전, 승인, 반려

    private LocalDateTime appDate; //결재완료일


    @Builder
    public Document(Long id, String title, String content, LocalDateTime createDate,
                    LocalDateTime updateDate, Member writer, Template template, int status, Long dno, Long sno) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.writer = writer;
        this.template = template;
        this.status = status;
        this.dno = dno;
        this.sno = sno;
    }
}


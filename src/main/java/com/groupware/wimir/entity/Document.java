package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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

    private LocalDate createDate; //작성일

    private LocalDate updateDate; //수정일

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer; // 작성자 (Member와 연관관계)

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tem_id")
    private Template template; // 양식

    private int status; // 1: 작성 상태, 0: 임시저장 상태

    private Long dno = 0L;  // 문서 번호(디폴트 값은 0)

    private Long sno = 0L; // 문서 임시저장 번호(디폴트 값은 0)

    private String result; //결재전, 결재중, 승인, 반려

    private LocalDate appDate; //결재완료일

    private Long tempNo; // 양식별 문서번호


    @Builder
    public Document(Long id, String title, String content, LocalDate createDate,
                    LocalDate updateDate, Member writer, Template template, int status, Long dno, Long sno, Long tempNo) {
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
        this.tempNo = tempNo;
    }
}


package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="document")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //문서 아이디

    private String title; //문서명

    private String content; //문서내용

    private LocalDateTime createDate; //작성일

    private LocalDateTime updateDate; //수정일

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer; // 작성자 (Member와 연관관계)

    private Long temId; //양식

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line; // 결재라인 (ApprovalLine와 연관관계)

//    private int appStatus; //결재상태 (0대기, 1승인, 2반려)

    @Builder
    public Document(Long id, String title, String content, LocalDateTime createDate,
                 LocalDateTime updateDate, Member writer, Long temId, Line line) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.writer = writer;
        this.temId = temId;
        this.line = line;
    }

}

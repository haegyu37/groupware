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

    private Long memberId; //작성자

    private Long temId; //양식

    private Long appId; //결재 아이디

    private int status; //문서상태 (0임시저장, 1작성완료)

    @Builder
    public Document(Long id, String title, String content, LocalDateTime createDate,
                 LocalDateTime updateDate, Long memberId, Long temId, Long appId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.memberId = memberId;
        this.temId = temId;
        this.appId = appId;
    }

}

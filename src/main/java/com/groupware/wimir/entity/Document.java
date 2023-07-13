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
    private Long id; //문서 아이디

    private String title; //문서 제목

    private String content; //문서 내용

    private LocalDateTime writeDate; //문서 작성일

    private LocalDateTime updateDate; //문서 수정일

    private String tem; //문서양식

    private int appStatus; //결재상태 (0:대기, 1:승인, 2:반려)

    private Long memberId; //직원(작성자) 아이디

    private Long lineId; //결재라인 아이디
    
//    private Line lineId; //결재라인 아이디

    private int docStatus; //문서 상태(0:임시저장, 1:작성완료)


}

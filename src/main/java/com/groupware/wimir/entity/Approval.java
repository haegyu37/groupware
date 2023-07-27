package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "approval")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "document_id")
    private Long document; //문서 아이디

//    private int result; //결재 결과 (0전, 1승인, 2반려)

    private String reason; //반려사유

    private LocalDateTime appDate; //결재완료일

    private Long memberId; //결재자

    private String name; //결재라인 이름

    private LocalDateTime doneDate; //결재자별 결재완료일

    private Long writer; //결재라인 작성자

    private String category; //결재라인 카테고리

    private int status; //결재 결과 (0전, 1승인, 2반려)




//    public Approval(Long document, List<Long> line, int approved, LocalDateTime approvalDate) {
//        this.document = document;
//        this.line = line;
//        this.approved = approved;
//        this.approvalDate = approvalDate;
//    }

}

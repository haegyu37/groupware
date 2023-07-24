package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "approval")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 승인 ID

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document; // 승인 대상 문서

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member approver; // 결재자

    private int status; // 결재상태(-1참조, 0대기, 1승인, 2반려)

    private LocalDateTime approvalDate; // 승인 날짜

    private int step; //결재순서

    private String name; //결재라인 이름

}

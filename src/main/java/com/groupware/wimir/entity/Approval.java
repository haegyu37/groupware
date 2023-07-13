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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document; // 승인 대상 문서

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id")
    private Member approver; // 승인자 정보

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "line_id")
    private Line line; // 결재라인 정보

    private int approvalStatus; // 승인 상태(0대기, 1승인, 2반려)

    private LocalDateTime approvalDate; // 승인 날짜






}

package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "approval")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private ApprovalLine line;

    private int approved; //결재 결과 (0전, 1승인, 2반려)

    private LocalDateTime approvalDate;


    public Approval(Document document, ApprovalLine line, int approved, LocalDateTime approvalDate) {
        this.document = document;
        this.line = line;
        this.approved = approved;
        this.approvalDate = approvalDate;
    }

}

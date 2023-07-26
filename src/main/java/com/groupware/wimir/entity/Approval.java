package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


@Entity
//@AllArgsConstructor
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

    @ElementCollection
    @JoinColumn(name = "approvers_id")
    private List<Long> approverIds;

//    //엔티티 나누기
//    @OneToMany(mappedBy = "approval")
//    private List<ApprovalLine> approvers;


    private int approved; //결재 결과 (0전, 1승인, 2반려)

    private LocalDateTime approvalDate;

    private String name; //결재라인 이름


    public Approval(Document document, List<Long> approverIds, int approved, LocalDateTime approvalDate, String name) {
        this.document = document;
        this.approverIds = approverIds;
        this.approved = approved;
        this.approvalDate = approvalDate;
        this.name = name;
    }

}

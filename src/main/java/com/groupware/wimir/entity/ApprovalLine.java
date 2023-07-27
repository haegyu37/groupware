//package com.groupware.wimir.entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
////@AllArgsConstructor
//@ToString
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "approvalLine")
//public class ApprovalLine {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long approverId;
//
//    @ManyToOne
//    @JoinColumn(name = "approval_id")
//    private Approval approval;
//
//    private LocalDateTime approvedDate;
//
//
//
//}

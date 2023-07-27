//package com.groupware.wimir.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@AllArgsConstructor
//@ToString
//@NoArgsConstructor
//@Getter
//@Setter
//@Table(name = "approval_line")
//public class ApprovalLine {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long memberId; // 결재자 (Member와 연관관계)
//
//    private LocalDateTime appDate;
//
//    private String status; //0결재 전 1승인 2반려
//
//    private String name; //결재라인 이름
//
//    private Long writer; // 작성자 (Member와 연관관계)
//
//    private String category;
//
//}
//
//

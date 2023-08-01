//package com.groupware.wimir.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//import java.util.List;
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
//    @OneToMany
//    private List<Member> approver; // 결재자 (Member와 연관관계)
//
//    private String name; //결재라인 이름
//
//    private Long writer; // 작성자
//
//    private String category;
//
//}
//
//

//
//package com.groupware.wimir.entity;
//
//import com.groupware.wimir.constant.AppStatus;
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@AllArgsConstructor
//@ToString
//@NoArgsConstructor
//@Getter @Setter
//@Table(name = "app")
//public class App {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id; //결재 아이디
//
//    @Column(name = "app_status")
//    @Enumerated(EnumType.STRING)
//    private AppStatus appStatus; //결재 전, 결재 중, 승인, 반려, 전결
//
//    @OneToOne
//    @JoinColumn(name = "doc_id")
//    private Document doc; //문서 아이디
//
//    @OneToOne
//    @JoinColumn(name = "line_id")
//    private Line line; //결재라인 아이디
//
//}
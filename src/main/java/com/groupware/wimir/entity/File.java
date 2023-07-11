//package com.groupware.wimir.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@AllArgsConstructor
//@ToString
//@NoArgsConstructor
//@Getter @Setter
//@Table(name = "file")
//public class File {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id")
//    private Long id; //파일 아이디
//
//    @Column(name = "name")
//    private String name; //파일 이름
//
//    @Column(name = "size")
//    private Long size; //파일 크기
//
//    @Column(name = "path")
//    private String path; //파일 경로
//
//    @OneToOne
//    @JoinColumn(name = "doc_id")
//    private Document doc; //문서 아이디
//
//}

package com.groupware.wimir.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "file")
@Getter
@Setter
@ToString
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //파일 아이디

    private String name; //파일 이름

    private Long size; //파일 크기

    private String path; //파일 경로

    @OneToOne
    @JoinColumn(name = "doc_id")
    private Document doc; //문서 아이디

}

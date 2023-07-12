package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "line")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //결재라인 아이디

    private String name; //결재라인명

    private Long memberId; //결재자 아이디

    private int isApp; //결재자 참조자 구분 (0: 결재자, 1: 참조자)

    private int step; //결재순서 (1~N)

}

package com.groupware.wimir.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "members")
@Component
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //직원 아이디

    private String no; //직원 사번(로그인)

    private String password; //직원 비밀번호

    private String name; //직원 이름

    @Enumerated(EnumType.STRING)
    private Team team; // 팀

    @Enumerated(EnumType.STRING)
    private Position position; // 직급
    //    1: 사원, 2:대리, 3:과장, 4:차장, 5:부장, 6:상무, 7:전무, 8:부사장, 9:사장

    @Enumerated(EnumType.STRING)
    private Authority authority; //직원 권한

    private String img; //직원사진

//    @ManyToOne
//    @JoinColumn(name = "approval_id")
//    private Approval approval; // 승인 대상 문서

    public void setPassword(String password) {
        this.password = password;
    }

    @Builder
    public Member(Long id, String no, String password, String name, Position position, Authority authority, Team team, String img) {
        this.id = id;
        this.no = no;
        this.password = password;
        this.name = name;
        this.position = position;
        this.authority = authority;
        this.team = team;
        this.img= img;
    }

}
package com.groupware.wimir.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //직원 아이디

    private String no; //직원 사번(로그인)

   private String password; //직원 비밀번호

    private String name; //직원 이름

    private String position; //직급

    private String part; //본부

    private String team; //팀

    private Authority authority; //직원 권한


    public void setPassword(String password){
        this.password = password;}

    @Builder
    public Member(Long id, String no, String password, String name, String position, Authority authority, String part, String team) {
        this.id = id;
        this.no = no;
        this.password = password;
        this.name = name;
        this.position = position;
        this.authority = authority;
        this.part = part;
        this.team = team;
    }
}
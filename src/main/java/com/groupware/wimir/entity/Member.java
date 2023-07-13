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
    @Column(name = "id")
    private Long id; //직원 아이디


    @Column(name = "no")
    private String no; //직원 사번(로그인)

    @Column(name = "password")
    private String password; //직원 비밀번호

    @Column(name = "name")
    private String name; //직원 이름


    @Column(name="position_id")
    private String position; //직급 아이디

    @Enumerated(EnumType.STRING)
    private Authority authority;    //직원 권한


    @Column(name="part_id")
    private String part; //직원 본부

    @Column(name="team_id")
    private String team; //직원 팀

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
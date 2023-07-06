package com.groupware.groupware.entity;

import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "no")
    private String no;      //사번, 로그인id

//    @Column(nullable = false)
//    private String email;

    @Column(nullable = false)
    private String password;    // 직원 비밀번호

    @Column(name = "name")
    private String name;        // 직원 이름

    @Column(name = "position")
    private String position;   //직원 직책

    @Enumerated(EnumType.STRING)
    private Authority authority;    //직원 권한

    @Column(name="part_id")
    private String part;

    public void setPassword(String password){
    this.password = password;}


    @Builder
    public Member(Long id, String no, String password, String name, String position,Authority authority, String part) {
        this.id = id;
        //this.email = email;
        this.no = no;
        this.password = password;
        this.name = name;
        this.position = position;
        this.authority = authority;
        this.part = part;
    }
}
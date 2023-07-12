package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users_img")
public class UsersImg {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //직원이미지 아이디

    @Column(name = "name")
    private String name; //직원이미지 이름

    @Column(name = "ori_name")
    private String oriName; //직원이미지 원본이름

    @Column(name = "path")
    private String path; //직원이미지 경로

    private Member member; //직원 아이디


}

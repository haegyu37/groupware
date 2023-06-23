package com.groupware.groupware.entity;

import lombok.*;
import javax.persistence.*;
import javax.persistence.Entity;
import com.groupware.groupware.users.Role;


@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")

public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="users_no")
    private int no;            //사번

    @Column(name="users_id")
    private Long id;            //id

    @Column(name="users_name")
    private String name;       //이름

    @Column(name="users_dep_id")
    private String dep_id;      //부서

    @Column(name="users_position")
    private String position;    //직급

    @Column(name="users_status")
    private String status;      //계정 상태

    @Enumerated(EnumType.STRING)
    @Column(name="users_role")
    private Role role;          // 마스터 계정 여부


    @Column(name="users_password")
    private String password;    // 비밀번호



    public String getUsername() {
        return this.name; // 사용자의 이름을 반환하도록 설정
    }

}

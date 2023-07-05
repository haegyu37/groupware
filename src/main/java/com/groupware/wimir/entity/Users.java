package com.groupware.wimir.entity;

import com.groupware.wimir.constant.AppStatus;
import com.groupware.wimir.constant.Role;
import com.groupware.wimir.constant.UserStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //직원 아이디

    @Column(name = "name")
    private String name; //직원 이름

    @Column(name = "com_id")
    private Long comId; //직원 사번

    @Column(name = "password")
    private String password; //직원 비밀번호

    @Column(name = "phone")
    private Long phone; //직원 연락처

    @Column(name = "position")
    private String position; //직원 직책

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus; //활성화, 비활성화

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role; //마스터, 일반계정

    @OneToOne
    @JoinColumn(name="part_id")
    private Part part; //직원 부서

    @OneToOne
    @JoinColumn(name="img_id")
    private UsersImg usersImg; //직원이미지 아이디


}

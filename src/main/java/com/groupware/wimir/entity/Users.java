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
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue
    private Long id; //직원 아이디

    @Column
    private String name; //직원 이름

    @Column
    private Long comId; //직원 사번

    @Column
    private String password; //직원 비밀번호

    @OneToOne
    @JoinColumn(name="part_id")
    private Part part; //직원 부서

    @Column
    private String position; //직원 직책

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus; //활성화, 비활성화

    @Enumerated(EnumType.STRING)
    private Role role; //마스터, 일반 계정


}

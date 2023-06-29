package com.groupware.groupware.entity;

import lombok.*;
import javax.persistence.*;
import com.groupware.groupware.users.Role;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "users_no")
    private Long usersNo;            // 사번

    @Column(name = "users_id")
    private String usersId;         // ID

    @Column(name = "users_password")
    private String usersPassword;   // 비밀번호

    @Column(name = "users_name")
    private String usersName;       // 이름

    @Column(name = "users_dep_id")
    private String usersDepId;      // 부서

    @Column(name = "users_position")
    private String usersPosition;   // 직급

    @Column(name = "users_status")
    private String usersStatus;     // 계정 상태

    @Enumerated(EnumType.STRING)
    @Column(name = "users_role")
    private Role role;              // 역할

    public Users(Long userNo, String usersId, String usersPassword, String usersName, String usersDepId, String usersPosition, String usersStatus, Role role) {
        this.usersNo = userNo;
        this.usersId = usersId;
        this.usersPassword = usersPassword;
        this.usersName = usersName;
        this.usersDepId = usersDepId;
        this.usersPosition = usersPosition;
        this.usersStatus = usersStatus;
        this.role = role;
    }
}

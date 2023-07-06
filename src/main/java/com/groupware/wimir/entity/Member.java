package com.groupware.wimir.entity;

import com.groupware.wimir.constant.Role;
//import com.groupware.wimir.constant.MemberStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "users")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //직원 아이디

    @Column(name = "name")
    private String name; //직원 이름

    @Column(name = "com_id")
    private Long comId; //직원 사번(로그인)

    @Column(name = "password")
    private String password; //직원 비밀번호

    @Column(name = "phone")
    private Long phone; //직원 연락처

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role; //마스터, 일반, 차단 계정

    @OneToOne
    @JoinColumn(name="part_id")
    private Part part; //직원 본부

    @OneToOne
    @JoinColumn(name="team_id")
    private Team team; //직원 팀

    @OneToOne
    @JoinColumn(name="img_id")
    private UsersImg usersImg; //직원이미지 아이디

    @OneToOne
    @JoinColumn(name="position_id")
    private Position position; //직급 아이디

}

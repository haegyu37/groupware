package com.groupware.wimir.entity;

//import com.groupware.wimir.constant.MemberStatus;
import com.groupware.wimir.constant.Authority;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "members")
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //직원 아이디

    private String name; //직원 이름

    private Long no; //직원 사번(로그인)

    private String password; //직원 비밀번호

    @Enumerated(EnumType.STRING)
    private Authority authority; //직원 권한

    private String part; //직원 본부

    private String team; //직원 팀

    private String usersImg; //직원이미지

    private String position; //직급




}

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
@Table(name = "userst")
@Builder
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //직원 아이디

    @Column(name = "name")
    private String name; //직원 이름

    @Column(name = "no")
    private Long no; //직원 사번(로그인)

    @Column(name = "password")
    private String password; //직원 비밀번호

    @Column(name = "authority")
    @Enumerated(EnumType.STRING)
    private Authority authority;
//
//    @OneToOne
//    @JoinColumn(name="part_id")
//    private Part part; //직원 본부
//
//    @OneToOne
//    @JoinColumn(name="team_id")
//    private Team team; //직원 팀
//
//    @OneToOne
//    @JoinColumn(name="img_id")
//    private UsersImg usersImg; //직원이미지 아이디
//
//    @OneToOne
//    @JoinColumn(name="position_id")
//    private Position position; //직급 아이디
//    private Long memberId;

}

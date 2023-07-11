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
@Table(name = "users")
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


//    @Transient
//    private Long positionId;

    @OneToOne
    @JoinColumn(name="position_id")
    private Position position; //직급 아이디

    @Enumerated(EnumType.STRING)
    private Authority authority;    //직원 권한

//    @Transient
//    private Long partId;

    @OneToOne
    @JoinColumn(name="part_id")
    private Part part; //직원 본부


//    @Transient
//    private Long teamId;


    @OneToOne
    @JoinColumn(name="team_id")
    private Team team; //직원 팀

    @OneToOne
    @JoinColumn(name="img_id")
    private UsersImg usersImg; //직원이미지 아이디


    @Column(name="status")
    private String status;      //재직 or 퇴사

    public void setPassword(String password){
        this.password = password;}


    @Builder
    public Member(Long id, String no, String password, String name, Position position, Authority authority, Part part, Team team, String status) {
        this.id = id;
        this.no = no;
        this.password = password;
        this.name = name;
        this.position = position;
        this.authority = authority;
        this.part = part;
        this.team = team;
        this.status = status;
    }
}
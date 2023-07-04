package com.groupware.wimir.entity;

import com.groupware.wimir.constant.AppStatus;
import com.groupware.wimir.constant.LineStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //결재라인 아이디

    @Column(name = "name")
    private String name; //결재라인명

    @Column(name = "step")
    private int step; //결제 단계 (1~N)

    @OneToOne
    @JoinColumn(name="users_id")
    private Users users; //직원 아이디

    @Column(name = "line_status")
    @Enumerated(EnumType.STRING)
    private LineStatus lineStatus; //결재자, 참조자


}

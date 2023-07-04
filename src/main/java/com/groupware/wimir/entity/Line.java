package com.groupware.wimir.entity;

import com.groupware.wimir.constant.AppStatus;
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
    @GeneratedValue
    private Long id; //결재라인 아이디

    @Column
    private String name; //결재라인명

    @Column
    private int step; //결제 단계 (1~N)

    @OneToOne
    @JoinColumn(name="users_id")
    private Users users; //직원 아이디

    @Enumerated(EnumType.STRING)
    private AppStatus appStatus; //결재자, 참조자


}

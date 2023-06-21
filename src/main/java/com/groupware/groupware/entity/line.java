package com.groupware.groupware.entity;

import com.groupware.groupware.constant.AppStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class line {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name; //결재라인명

    @Column
    private int step; //결제 단계 (1~N)

    @Column
    private Long usersId; //결재자

    @Enumerated(EnumType.STRING)
    private AppStatus appStatus; //결재자, 참조자


}

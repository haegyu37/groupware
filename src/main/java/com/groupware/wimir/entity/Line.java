package com.groupware.wimir.entity;

import com.groupware.wimir.constant.LineStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "linet")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //결재라인 아이디

    @Column(name = "name")
    private String name; //결재라인명

    @Column(name = "step")
    private int step; //결재순서 (1~N)

    private String lineStatus; //결재자, 참조자

    private String member; //직원 아이디

    private Long document; // 문서 아이디


}

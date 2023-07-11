package com.groupware.wimir.entity;

import com.groupware.wimir.constant.LineStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "line")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //결재라인 아이디

    @Column(name = "name")
    private String name; //결재라인명

    @Column(name = "step")
    private int step; //결재순서 (1~N)

    @Column(name = "line_status")
    @Enumerated(EnumType.STRING)
    private LineStatus lineStatus; //결재자, 참조자

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member; //직원 아이디

    @OneToOne
    @JoinColumn(name = "doc_id")
    private Document document; // 문서 아이디



}

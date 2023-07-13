package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "line")
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 결재라인 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document; // 결재 대상 문서

    private String lineName; // 결재라인 이름

    private int step; // 결재 순서

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    private List<Approval> approvals = new ArrayList<>(); // 결재자들의 승인 정보

    @ManyToMany
    @JoinTable(
            name = "approval_line_viewer",
            joinColumns = @JoinColumn(name = "approval_line_id"),
            inverseJoinColumns = @JoinColumn(name = "viewer_id")
    )
    private List<Member> viewers = new ArrayList<>(); // 참조자 목록

}

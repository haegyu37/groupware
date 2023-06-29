package com.groupware.groupware.entity;

import com.groupware.groupware.constant.AppStatus;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
public class App {

    @Id
    @GeneratedValue
    private Long id; //결재 아이디

    @Column
    private Long docId; //문서 아이디

    @Column
    private Long lineId; //결재자

    @Enumerated(EnumType.STRING)
    private AppStatus appStatus; //결재 전, 결재 중, 승인, 반려, 전결

}

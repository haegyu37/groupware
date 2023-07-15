package com.groupware.wimir.entity;

import lombok.*;
import org.odftoolkit.odfdom.type.DateTime;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "app")
public class App {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //결재 아이디

    private Long doc_id; //문서 아이디

    private int status; //결재 결과 (0: 대기중, 1: 승인, 2: 반려)

    private LocalDateTime receiveDate; //받은 일자

    private LocalDateTime signDate; //결재 일자

    private Long member_id; //직원 아이디

    private String position; //직급








}

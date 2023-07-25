package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "approval")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; // 승인 ID

    private LocalDateTime approvalDate; // 승인 날짜

    private String reason; // 반려사유

    private int status; // 결재상태(0대기, 1완료)

    private int result; //결재결과(0반려, 1승인)

    private int step; //결재순서

    private String name; //결재라인 이름

    private Long approver;

    private Long document;





}

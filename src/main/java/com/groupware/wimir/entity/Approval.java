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

//    @ManyToOne
//    @JoinColumn(name = "document_id")
//    private Document document; // 승인 대상 문서

//    @ManyToOne
//    @JoinColumn(name = "member_id")
//    private Member memberId; // 결재자

    private int status; // 결재상태(0대기, 1승인, 2반려)

    private LocalDateTime approvalDate; // 승인 날짜

    private int step; //결재순서

    private String name; //결재라인 이름

    //깃 가져온거
    // Approval
//    private LocalDateTime appWriteDate;

    private Long approver;

    private Long document;

//    private LocalDateTime ApprovalDate; // 결재결과일시

    private String Reason; // 반려사유

//    private int appWriterNo; //member.getUser_no 글쓴이

//    private String appCheckProgress; //APP_CHECK_PROGRESS IN ('결재대기', '결재중', '결재완료')

//    private String appKinds; // 결재 종류

//    private String appPresent;

//    private int userNo;

//    private String deptName;

//    private String userName;

//    private int rowNum;

//    private String rank;


}

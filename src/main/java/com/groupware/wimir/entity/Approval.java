package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "approval")
public class Approval {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long document; //문서 아이디

    private String reason; //반려사유

    private LocalDate appDate; //결재완료일

    private Long memberId; //결재자

    private String name; //결재라인 이름

    private Long writer; //결재라인 작성자

    private Long lineId; //결재라인 아이디

    private String category; //결재라인 카테고리

    private int status; //결재자별 결재상태 (0전, 1승인, 2반려)

    private String current; //현재 결재순서(Y/N)

    private String refer; //"참조" 참조자

    public static Map<Long, List<Approval>> groupByLineId(List<Approval> approvals) {
        return approvals.stream()
                .filter(approval -> approval.getLineId() != null) // lineId가 null이 아닌 경우만 필터링
                .collect(Collectors.groupingBy(Approval::getLineId));
    }

    public static Map<Long, List<Approval>> groupByLineName(List<Approval> approvals) {
        return approvals.stream()
                .filter(approval -> approval.getName() != null) // lineId가 null이 아닌 경우만 필터링
                .collect(Collectors.groupingBy(Approval::getLineId));
    }


}

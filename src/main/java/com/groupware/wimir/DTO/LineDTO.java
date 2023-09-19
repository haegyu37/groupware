package com.groupware.wimir.DTO;

import lombok.*;

import java.util.List;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
@ToString
@Getter
@Setter
public class LineDTO {

    private List<Long> approvers;
    private String name;
    private Long writer;
    private String category;
}
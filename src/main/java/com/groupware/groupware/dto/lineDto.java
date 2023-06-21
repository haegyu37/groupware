package com.groupware.groupware.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class lineDto {

    private String name; //결재라인명

    private int step; //결제 단계 (1~N)

    private Long usersId; //결재자

}

package com.groupware.wimir.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class AppDTO {

    private Long docId; //문서 아이디

    private Long lineId; //결재자

}

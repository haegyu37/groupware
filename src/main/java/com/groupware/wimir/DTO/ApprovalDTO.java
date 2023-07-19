package com.groupware.wimir.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter @Setter
public class ApprovalDTO {

    private Long docId; //문서 아이디

    private Long lineId; //결재자

}

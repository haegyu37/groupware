package com.groupware.wimir.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter @Setter
public class LineDTO {

    private String name; //결재라인명

    private int step; //결제 단계 (1~N)

    private Long usersId; //결재자

}

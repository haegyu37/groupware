<<<<<<< HEAD
//package com.groupware.wimir.dto;
//
//import lombok.AllArgsConstructor;
//import lombok.ToString;
//
//@AllArgsConstructor
//@ToString
//public class LineDTO {
//
//    private String name; //결재라인명
//
//    private int step; //결제 단계 (1~N)
//
//    private Long usersId; //결재자
//
//}
=======
package com.groupware.wimir.dto;

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
>>>>>>> d210e306cbd058c79f80ea7fb5090b2384db43f4

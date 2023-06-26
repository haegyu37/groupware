package com.groupware.groupware.dto;

import com.groupware.groupware.users.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private int userNo;             // 사번
    private Long userId;            // ID
    private String userPassword;    // 비밀번호
    private String userName;        // 이름
    private String usersDepId;       // 부서
    private String usersPosition;    // 직급
    private String usersStatus;      // 계정 상태
    private Role role;          // 마스터 계정 여부

}
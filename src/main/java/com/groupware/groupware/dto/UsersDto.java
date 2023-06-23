package com.groupware.groupware.dto;

import com.groupware.groupware.users.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private int no;             // 사번
    private Long id;            // ID
    private String name;        // 이름
    private String depId;       // 부서
    private String position;    // 직급
    private String status;      // 계정 상태
    private Role role;          // 마스터 계정 여부
    private String password;    // 비밀번호
}
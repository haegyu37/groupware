package com.groupware.groupware.dto;

import com.groupware.groupware.users.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginRequest {
    private Long usersno;
    private String usersId;  // 수정된 필드 이름
    private String usersPassword;
    private String usersName;
    private String usersDepId;
    private String usersPositon;
    private String usersPosition;
    private String usersStatus;
    private String role;
}

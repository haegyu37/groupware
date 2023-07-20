package com.groupware.wimir.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {
    //    private String no;
//    private String exPassword;
    private String newPassword;
}
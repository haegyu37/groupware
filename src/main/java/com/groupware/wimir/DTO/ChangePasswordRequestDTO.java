package com.groupware.wimir.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequestDTO {
    private MultipartFile image;
    private String newPassword;
}
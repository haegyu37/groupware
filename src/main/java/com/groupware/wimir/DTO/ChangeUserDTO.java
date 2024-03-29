package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Team;
import com.groupware.wimir.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeUserDTO {
    private String newPassword;
    private Position position;
    private Team team;
    private String name;
}
package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Team;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MemberSerchDTO {
    private String searchQuery="";
    private Team team;
    private String searchBy;
}

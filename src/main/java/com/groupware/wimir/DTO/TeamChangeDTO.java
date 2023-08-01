package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamChangeDTO {
    private Team team;
}
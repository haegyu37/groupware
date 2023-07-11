package com.groupware.wimir.dto;

import com.groupware.wimir.constant.Authority;
import com.groupware.wimir.entity.*;
import lombok.*;


//Response를 보낼때 쓰이는 dto다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private Long id;
    private Long no;
    private String name;
    private Position position;
    private Part part;
    private Team team;
    private Authority authority;
    private UsersImg usersImg;

    public static MemberResponseDTO of(Member member) {
        return MemberResponseDTO.builder()
                .no(member.getNo())
                .name(member.getName())
                .position(member.getPosition())
                .part(member.getPart())
                .team(member.getTeam())
                .authority(member.getAuthority())
                .build();
    }
}
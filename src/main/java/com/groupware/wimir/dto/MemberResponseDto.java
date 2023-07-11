package com.groupware.wimir.dto;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Part;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import lombok.*;


//Response를 보낼때 쓰이는 dto다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private String no;
    private String name;
    private Position position;
    private Part part;
    private Team team;
    private String status;

    public static MemberResponseDTO of(Member member) {
        return MemberResponseDTO.builder()
                //.email(member.getEmail())
                .no(member.getNo())
                .name(member.getName())
                .position(member.getPosition())
                .part(member.getPart())
                .team(member.getTeam())
                .status(member.getStatus())
                .build();
    }
}
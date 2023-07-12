package com.groupware.wimir.dto;

import com.groupware.wimir.entity.Member;
import lombok.*;

import java.util.List;


//Response를 보낼때 쓰이는 dto다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private String no;
    private String name;
    private String position;
    private String part;
    private String team;

    public static MemberResponseDTO of(Member member) {
        return MemberResponseDTO.builder()
                .no(member.getNo())
                .name(member.getName())
                .position(member.getPosition())
                .part(member.getPart())
                .team(member.getTeam())
                .build();
    }
}
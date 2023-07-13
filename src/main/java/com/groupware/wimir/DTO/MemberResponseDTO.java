package com.groupware.wimir.dto;

import com.groupware.wimir.constant.Authority;
import com.groupware.wimir.entity.*;
import lombok.*;

import java.util.List;


//Response를 보낼때 쓰이는 dto다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private Long id;
    private Long no;
    private String name;
//    private String password;
    private String position;
    private String part;
    private String team;
    private Authority authority;


    public static MemberResponseDTO of(Member member) {
        return MemberResponseDTO.builder()
                .no(member.getNo())
                .name(member.getName())
//                .password(member.getPassword())
                .position(member.getPosition())
                .part(member.getPart())
                .team(member.getTeam())
                .authority(member.getAuthority())
                .build();
    }

}
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
    private Long position;
    private Long part;
    private Long team;
    private Authority authority;

//    private List<Part> parts;
//    private List<Team> teams;
//    private List<Position> positions;

//    public static MemberResponseDTO of(Member member) {
//        return MemberResponseDTO.builder()
//                .no(member.getNo())
//                .name(member.getName())
////                .password(member.getPassword())
//                .position(member.getPosition().getId())
//                .part(member.getPart().getId())
//                .team(member.getTeam().getId())
//                .authority(member.getAuthority())
//                .build();
//    }

//    public void setParts(List<Part> parts) {
//        this.parts = parts;
//    }
//
//    public void setTeams(List<Team> teams) {
//        this.teams = teams;
//    }
//
//    public void setPositions(List<Position> positions) {
//        this.positions = positions;
//    }
}
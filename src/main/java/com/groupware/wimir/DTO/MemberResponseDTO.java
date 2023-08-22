package com.groupware.wimir.DTO;
import com.groupware.wimir.entity.*;
import lombok.*;

import java.util.List;
import java.util.Map;


//Response를 보낼때 쓰이는 dto다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDTO {
    private Long id;
    private String no;
    private String name;
    private Position position;
    private Team team;
    private Authority authority;
    private String imgUrl;
//    private String password;
//    private ProfileDTO profileDTO;
//    private Long imgId;


//    private Long imgId;


    public static MemberResponseDTO of(Member member) {
        return MemberResponseDTO.builder()
                .id(member.getId())
                .no(member.getNo())
                .name(member.getName())
                .position(member.getPosition())
                .team(member.getTeam())
                .authority(member.getAuthority())
//                .profileDTO(profile.getImgUrl())
//                .img(member.getImg())
                .build();
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

}
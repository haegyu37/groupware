package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;


//Response를 보낼때 쓰이는 dto다.
@Getter @Setter
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
    private String image;

    public static MemberResponseDTO of(Member member) {

        return MemberResponseDTO.builder()
                .id(member.getId())
                .no(member.getNo())
                .name(member.getName())
                .position(member.getPosition())
                .team(member.getTeam())
                .authority(member.getAuthority())
                .build();
    }

}


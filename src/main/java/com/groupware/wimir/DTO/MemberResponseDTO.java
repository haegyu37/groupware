package com.groupware.wimir.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.ProfileRepository;
import lombok.*;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;


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
    private Resource image;

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


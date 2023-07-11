package com.groupware.wimir.dto;

import com.groupware.wimir.constant.Authority;
import com.groupware.wimir.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;


//Request를 받을 때 쓰이는 dto다. UsernamePasswordAuthenticationToken를 반환하여
// 아이디와 비밀번호가 일치하는지 검증하는 로직을 넣을 수 있게 된다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDTO {
    //private String email;
    private String no;
    private String password;
    private String name;
    private Long positionId; // 직급 아이디
    private Long partId; // 본부 아이디
    private Long teamId; // 팀 아이디
    private String status;

    public Member toMember(PasswordEncoder passwordEncoder) {
        Position position = new Position();
        position.setId(positionId);

        Part part = new Part();
        part.setId(partId);

        Team team = new Team();
        team.setId(teamId);



        return Member.builder()
                //.email(email)
                .no(no)
                .password(passwordEncoder.encode(password))
                .name(name)
                .position(position)
                .authority(Authority.ROLE_ADMIN)
                .part(part)
                .team(team)
                .status(status)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(no, password);
    }
}
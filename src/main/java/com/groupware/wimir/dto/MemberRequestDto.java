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
    private String position; // 직급 이름
    private String part; // 본부 이름
    private String team; // 팀 이름
    private String status;

    public Member toMember(PasswordEncoder passwordEncoder) {

        return Member.builder()
                .no(no)
                .password(passwordEncoder.encode(password))
                .name(name)
                .position(position)
                .authority(Authority.ROLE_ADMIN)
                .part(part)
                .team(team)
                .build();
    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(no, password);
    }



}
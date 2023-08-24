package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Authority;
import com.groupware.wimir.entity.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;


//Request를 받을 때 쓰이는 dto다. UsernamePasswordAuthenticationToken를 반환하여
// 아이디와 비밀번호가 일치하는지 검증하는 로직을 넣을 수 있게 된다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDTO {
    private Long id;
    private String no;
    private String password;
    private String name;
    private Position position; // 직급 이름
    private Team team; // 팀 이름
    private MultipartFile image;

    public Member toMember(PasswordEncoder passwordEncoder) {

        return Member.builder()
                .id(id)
                .no(no)
                .password(passwordEncoder.encode(password))
                .name(name)
                .position(position)
//                .authority(Authority.ADMIN)
                .authority(Authority.USER)
                .team(team)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(no, password);
    }


}
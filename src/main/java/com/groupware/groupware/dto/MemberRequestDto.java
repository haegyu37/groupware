package com.groupware.groupware.dto;

import com.groupware.groupware.entity.Authority;
import com.groupware.groupware.entity.Member;
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
public class MemberRequestDto {
    //private String email;
    private String no;
    private String password;
    private String name;
    private String position;
    private String part;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                //.email(email)
                .no(no)
                .password(passwordEncoder.encode(password))
                .name(name)
                .position(position)
                .part(part)
                .authority(Authority.ROLE_USER)
                //.authority(Authority.ROLE_ADMIN)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(no, password);
    }
}
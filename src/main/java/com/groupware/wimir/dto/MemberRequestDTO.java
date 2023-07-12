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
    private Long id; //직원 아이디
    private Long no; //직원 사번
    private String password; //직원 비밀번호
    private String name; //이름
    private Authority authority; //권한

//    public Member toMember(PasswordEncoder passwordEncoder) {
//        return Member.builder()
//                .id(id)
//                .no(no)
//                .password(passwordEncoder.encode(password))
//                .name(name)
//                .position(position)
////                .part(part)
//                .team(team)
////                .authority(Authority.ROLE_USER)
//                .authority(Authority.ROLE_ADMIN)
//                .build();
//    }
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(no, password);
    }

//    public Member toEntity() {
//        return new Member(null, name, no, password, authority, team, position);
//    }
}
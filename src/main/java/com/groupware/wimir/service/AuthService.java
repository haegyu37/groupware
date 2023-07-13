package com.groupware.wimir.service;

import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.TokenDTO;
import com.groupware.wimir.jwt.TokenProvider;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    //signup 회원가입
    public MemberResponseDTO signup(MemberRequestDTO requestDto) {
        if (memberRepository.existsByNo(requestDto.getNo())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDTO.of(memberRepository.save(member));
    }

    // MemberRequestDto에 있는 메소드 toAuthentication를 통해 생긴 UsernamePasswordAuthenticationToken 타입의 데이터를 가짐
    // 주입받은 Builder를 통해 AuthenticationManager를 구현한 ProviderManager를 생성
    // 이후 ProviderManager 는 데이터를 AbstractUserDetailsAuthenticationProvider의 자식 클래스인 DaoAuthenticationProvider를 주입받아 호출
    // DaoAuthenticationProvider 내부에 있는 authenticate 에서 retrieveUser을 통해 DB에서 User의 비밀번호가 실제 비밀번호가 맞는지 비교
    // retrieveUser에서는 DB에서의 User를 꺼내기 위해, CustomUserDetailService에 있는 loadUserByUsername을 가져와 사용
    public TokenDTO login(MemberRequestDTO requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

}
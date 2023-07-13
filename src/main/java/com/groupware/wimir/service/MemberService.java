package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.dto.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    //getMyInfoBySecurity는 헤더에 있는 token값을 토대로 Member의 data를 건내주는 메소드

    public MemberResponseDTO getMyInfoBySecurity() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .map(MemberResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
    }


//    @Transactional
//    public MemberResponseDTO changeMemberPassword(String no, String exPassword, String newPassword) {
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
//        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
//            throw new RuntimeException("비밀번호가 맞지 않습니다");
//        }
//        member.setPassword(passwordEncoder.encode((newPassword)));
//        return MemberResponseDTO.of(memberRepository.save(member));
//    }
}

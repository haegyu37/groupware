package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    //등록된 모든 사원 정보 조회
    public List<MemberResponseDTO> getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream()
                .map(MemberResponseDTO::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public MemberResponseDTO changeMemberPassword(String newPassword) {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        Member member = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));

        member.setPassword(passwordEncoder.encode(newPassword));
        Member updatedMember = memberRepository.save(member);

        return MemberResponseDTO.of(updatedMember);

    }
    @Transactional
    public MemberResponseDTO changeUserPasswordByAdmin(Long memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        member.setPassword(passwordEncoder.encode(newPassword));
        Member updatedMember = memberRepository.save(member);

        // 변경된 사용자 정보로 인증 객체 갱신
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                updatedMember.getId(), newPassword, SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return MemberResponseDTO.of(updatedMember);
    }

    // ID로 회원 조회
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 직원을 찾을 수 없습니다."));
    }

    public List<Member> getAllMembersByTeam() {
        List<Member> members = memberRepository.findAll();
        members.sort(Comparator.comparing((Member member) -> member.getTeam() == null ? 0 : 1)
                .thenComparing(Member::getTeam, Comparator.nullsLast(Comparator.naturalOrder()))
                .thenComparing(Member::getPosition, Comparator.comparingInt(Position::getValue)));
        return members;
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("Member not found with ID: " + memberId));
    }


}


//    @Transactional
//    public MemberResponseDTO changeMemberPassword(String exPassword, String newPassword) {
//        Member member = memberRepository.findById(SecurityUtil.getCurrentMemberId()).orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));
//        if (!passwordEncoder.matches(exPassword, member.getPassword())) {
//            throw new RuntimeException("비밀번호가 맞지 않습니다");
//        }
//        member.setPassword(passwordEncoder.encode((newPassword)));
//        return MemberResponseDTO.of(memberRepository.save(member));
//    }


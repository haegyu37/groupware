package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.ChangeUserDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.DTO.MemberSerchDTO;
import com.groupware.wimir.DTO.ProfileDTO;
import com.groupware.wimir.entity.*;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.persistence.EntityNotFoundException;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Comparator;
import java.util.List;
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
    public MemberResponseDTO changeUserPasswordByAdmin(Long memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        if (newPassword == null || newPassword.isEmpty()) {
            throw new IllegalArgumentException("새 비밀번호를 입력해주세요");
        }

        member.setPassword(passwordEncoder.encode(newPassword));
        Member updatedMember = memberRepository.save(member);

        // 변경된 사용자 정보로 인증 객체 갱신
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                updatedMember, null, SecurityContextHolder.getContext().getAuthentication().getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return MemberResponseDTO.of(updatedMember);
    }


    // 직급 변경
    @Transactional
    public MemberResponseDTO changeUserPositionByAdmin(Long memberId, Position newPosition) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        member.setPosition(newPosition);
        Member updatedMember = memberRepository.save(member);

        // 변경된 사용자 정보로 MemberResponseDTO 생성
        return MemberResponseDTO.of(updatedMember);
    }

    // 팀명 변경
    @Transactional
    public MemberResponseDTO changeUserTeamByAdmin(Long memberId, Team newTeam) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다"));

        member.setTeam(newTeam);
        Member updatedMember = memberRepository.save(member);

        // 변경된 사용자 정보로 MemberResponseDTO 생성
        return MemberResponseDTO.of(updatedMember);
    }

    @Transactional
    // 사용자의 권한을 ROLE_BLOCK으로 업데이트하는 메서드 추가
    public Member updateUserAuthorityToBlock(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        member.setAuthority(Authority.BLOCK);
        memberRepository.save(member);
        return member;
    }

    @Transactional
    //직원 삭제
    public Member deleteMember(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        member.setAuthority(Authority.DELETE);
        memberRepository.save(member);
        return member;
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


    //접속차단 해제
    @Transactional
    public Member updateBlockAuthorityToUser(Long userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        member.setAuthority(Authority.USER);
        return memberRepository.save(member);
    }


    //검색
    @Transactional(readOnly = true)
    public List<Member> getMembers(MemberSerchDTO memberSerchDTO){
        return memberRepository.getMembers(memberSerchDTO);
    }

}


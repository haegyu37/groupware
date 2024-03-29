package com.groupware.wimir.service;

import com.groupware.wimir.DTO.MemberRequestDTO;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.DTO.TokenRequestDTO;
import com.groupware.wimir.DTO.TokenDTO;
import com.groupware.wimir.entity.Authority;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Profile;
import com.groupware.wimir.entity.RefreshToken;
import com.groupware.wimir.jwt.TokenProvider;
import com.groupware.wimir.jwt.TokenStatus;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.repository.ProfileRepository;
import com.groupware.wimir.repository.RefreshTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {
    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final ProfileService profileService;
    private final ProfileRepository profileRepository;


    public MemberResponseDTO signup(MemberRequestDTO requestDto, MultipartFile multipartFile) throws Exception {
        if (memberRepository.existsByNo(requestDto.getNo())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        // 직원 등록
        Member member = requestDto.toMember(passwordEncoder);
        member = memberRepository.save(member);

        // 이미지 저장 (이미지가 제공된 경우에만 처리)
        if (multipartFile != null) {
            System.out.println("사진1" + multipartFile);
            Profile profile = new Profile();
            profile.setMember(member);
            profileService.saveProfile(profile, multipartFile);
        }

        // MemberResponseDTO로 변환하여 반환
        return MemberResponseDTO.of(member);
    }

    @Transactional
    public TokenDTO login(MemberRequestDTO requestDto) {
        //  Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        // 실제로 검증 (사용자 비밀번호 체크) 이 이루어지는 부분
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        //  인증 정보를 기반으로 JWT 토큰 생성
        TokenDTO tokenDto = tokenProvider.generateTokenDto(authentication);
        // RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 토큰 발급
        return tokenDto;
    }

    // 토큰 갱신
    public TokenDTO refresh(TokenRequestDTO tokenRequestDto) {

        //Refresh Token 검증
        TokenStatus.StatusCode tokenStatusCode = tokenProvider.validateToken(tokenRequestDto.getRefreshToken());
        if (tokenStatusCode != TokenStatus.StatusCode.OK) {
            throw new RuntimeException("유효하지 않은 리프레시 토큰입니다.");
        }

        //  Access Token 에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 저장소에서 Member ID 를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃 된 사용자입니다."));

        // Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 새로운 토큰 생성
        TokenDTO tokenDTO = tokenProvider.generateTokenDto(authentication);

        //  저장소 정보 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDTO.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        return tokenDTO;
    }


}





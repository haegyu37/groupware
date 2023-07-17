package com.groupware.wimir.jwt;

import com.groupware.wimir.DTO.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;


import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import java.util.logging.Logger;
@Slf4j
@Component
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";   //사용자 권한(authorities) 식별하는데 사용
    private static final String BEARER_TYPE = "bearer";     // 토큰유형 지정시 사용 Oauth 2.0 인증 프로토콜에서 사용되는 토큰 유형
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 360;        // 액세스토큰 만ㄹ시간

    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7; // 7 days

    private final Key key;  //토큰 생성시 사용할 키




    // @Value는 `springframework.beans.factory.annotation.Value` jwt.secret 프로퍼티 값을 주입받기 위해 사용
    //     * @param secretKey
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);        //BASE64 디코딩 수행시 사용
        this.key = Keys.hmacShaKeyFor(keyBytes);        //키를생성
    }   //생성자를 통해 secretKey 값을 받아와 디코딩하고, 그 결과로 HMAC-SHA키를 생성하여 key 에할당하는 역할을 함 토큰의 암호화 및 복호화에 사용


    // 토큰 생성 Authentication 인터페이스를 확장한 매개변수를 받아서 그 값을 string으로 변환
    // 이후 현재시각과 만료시각을 만든 후 jwts의 builder를 이용하여 token을 생성한 다음 tokendto에 생성한 token의 정보를 넣는다
    public TokenDTO generateTokenDto(Authentication authentication) {

        String authorities = authentication.getAuthorities().stream()   //사용자의 권한정보 추출
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();          //현재시간 가져옴, 현재 시간을 밀리초 단위로 반환


        Date tokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);     // 액세스 토큰 만료시간을 더하여 만료시간 설정

        System.out.println(tokenExpiresIn);

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())       //메서드로 사용자 이름 설정
                .claim(AUTHORITIES_KEY, authorities)        // 권한정보 설정
                .setExpiration(tokenExpiresIn)              // 토큰의 만료시간 설정
                .signWith(key, SignatureAlgorithm.HS512)    // 키오 서명 알고리즘을 지정하여 토큰 서명
                .compact();                                 // 최종적인 토큰 문자열 생성


        String refreshToken = generateRefreshToken();

        return TokenDTO.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .tokenExpiresIn(tokenExpiresIn.getTime())
                .build();
    }       // 주어진 인증정보 기반으로 액세스 토큰 생성하고 TokenDTO 객체로 변환하여 반환

    private String generateRefreshToken() {
        long now = System.currentTimeMillis();
        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }





    //getAuthentication 토큰을 받았을 때 토큰의 인증을 꺼내는 메소드
    // parseClaims 메소드로 string 형태의 토큰을 claims형태로 생성한다. 다음 auth가 없으면 exception을 반환
    //GrantedAuthority을 상속받은 타입만이 사용 가능한 Collection을 반환한다.
    // 그리고 stream을 통한 함수형 프로그래밍으로 claims형태의 토큰을 알맞게 정렬한 이후 SimpleGrantedAuthority형태의 새 list 생성
    // 여기에는 인가가 들어가있으며 SimpleGrantedAuthority은 GrantedAuthority을 상속받았기 때문에 가능하다.
    // 이후 spring security에서 유저의 정보를 담는 인터페이스인 UserDetails에 token에서 발췌한 정보와, 아까 생성한 인가를 넣고,
    // 이를 다시 UsernamePasswordAuthenticationToken안에 인가와 같이넣고 반환
    //UsernamePasswordAuthenticationToken인스턴스는 UserDetails를 생성해서 후에 SecurityContext에 사용하기 위해 만든 절차
    //SecurityContext는 Authentication객체를 저장하기 때문
    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);           //토큰 파싱하여 클레임 객체를 얻음

        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        //권한 정보를 문자열로부터 추출하여 GrantAuthority 객체들의 컬렉션으로 변환. 권한 정보는 쉼표로 구분된 문자열 형태로 토큰에 저장되어있음
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities); // 사용자 이름을 추출하여 User객체 생성

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // validateToken 토큰을 검증하기 위한 메소드
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    //parseClaim 토큰을 claims형태로 만든 메소드 이를 통해 위에서 권한 정보가 있는지 없는지 체크 가능
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    }

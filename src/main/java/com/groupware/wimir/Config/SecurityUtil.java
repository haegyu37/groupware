package com.groupware.wimir.Config;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


// SecurityContextHolder를 사용하여 인증된 사용자 정보가 저장되는 시점을 다룸
// Request가 들어오면 JwtFilter의 doFilter에서 저장되는데 거기에 있는 인증정보를 꺼내서, Long 타입으로 파싱하여 반환
public class SecurityUtil {


    // SecurityContext 에 유저 정보가 저장되는 시점
    // Request 가 들어올 때 JwtFilter 의 doFilter 에서 저장
    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Long currentUserName = Long.parseLong(authentication.getName());
            return currentUserName;
        } else {
            throw new RuntimeException("No User");
        }
    }
}
package com.groupware.wimir.Config;

import com.groupware.wimir.entity.Authority;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.jwt.JwtAccessDeniedHandler;
import com.groupware.wimir.jwt.JwtAuthenticationEntryPoint;
import com.groupware.wimir.jwt.TokenProvider;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;


@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;                              //토큰생성및 검증담당
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;  // 인증되지않은 요청에대한처리 담당
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;            // 인가 되지 않은 요청에 대한 처리

    //request로부터 받은 비밀번호를 암호화하기 위해 PasswordEncoder 빈 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    //filterChain(HttpSecurity http)- HttpSecurity를 구성하는 메서드 메서드에서 HTTP 요청에 대한 보안 설정 구성
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //세션 없이 REST API를 통해 토큰을 주고받기위해

                .and()
                .apply(new JwtSecurityConfig(tokenProvider))
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                .antMatchers("/admin/**").hasAuthority("ADMIN") //ROLE_ADMIN 계정만 admin에 접근 가능함
                .anyRequest().permitAll()

                .and()
                .cors()
                .configurationSource(corsConfigurationSource());


        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

//        config.addAllowedOrigin("*"); // 모든 도메인허용
        config.addAllowedOrigin("http://222.119.100.90/"); // 모든 도메인허용
        config.addAllowedMethod("*"); // 모든 메소드 허용.
        config.addAllowedHeader("*");
        config.setAllowCredentials(true);

        //config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // 허용할 헤더 추가
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
package com.groupware.wimir.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        // 유효한 자격증명을 제공하지 않고 접근하려 할때 401
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // 오류 메시지 작성
        String errorMessage = "인증에 실패하였습니다.";
        String jsonErrorResponse = "{\"error\": \"Unauthorized\", \"message\": \"" + errorMessage + "\"}";

        PrintWriter writer = response.getWriter();
        writer.write(jsonErrorResponse);
        writer.flush();

    }

    // AuthenticationException에서 오류 메시지 추출
    private String extractErrorMessage(AuthenticationException authException) {
        String errorMessage = "인증에 실패하였습니다.";
        if (authException.getCause() instanceof JwtException) {
            // JWT 예외가 발생한 경우
            JwtException jwtException = (JwtException) authException.getCause();
            if (jwtException instanceof ExpiredJwtException) {
                errorMessage = "토큰이 만료되었습니다.";
            } else if (jwtException instanceof UnsupportedJwtException) {
                errorMessage = "지원되지 않는 JWT 토큰입니다.";
            } else if (jwtException instanceof io.jsonwebtoken.security.SecurityException) {
                errorMessage = "잘못된 JWT 서명입니다.";
            } else {
                errorMessage = "JWT 토큰 파싱 오류";
            }
        }
        return errorMessage;
    }


}

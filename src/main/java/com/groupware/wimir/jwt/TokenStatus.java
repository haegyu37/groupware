package com.groupware.wimir.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.groupware.wimir.jwt.TokenStatus.StatusCode.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class TokenStatus {
    public enum StatusCode {
        OK,
        EXPIRED,
        REFRESH_EXPIRED,
        UNAUTHORIZED,
        UNKNOWN
    }
    private StatusCode statusCode;
    private String message;
    public static TokenStatus of(StatusCode status){
        return makeTokenStatus(status);
    }
    private static TokenStatus makeTokenStatus(StatusCode status) {
        if (OK.equals(status)){
            return new TokenStatus(OK, "유효한 JWT 토큰입니다.");
        }
        if (UNAUTHORIZED.equals(status)){
            return new TokenStatus(UNAUTHORIZED, "잘못된 JWT 서명입니다");
        }
        if (EXPIRED.equals(status)){
            return new TokenStatus(EXPIRED,"만료된 JWT 토큰입니다.");
        }
        return new TokenStatus(UNKNOWN, "JWT 토큰이 잘못되었습니다.");
    }
}

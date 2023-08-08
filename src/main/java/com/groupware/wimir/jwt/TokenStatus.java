package com.groupware.wimir.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.groupware.wimir.jwt.TokenStatus.StatusCode.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenStatus {
    public enum StatusCode {
        OK,
        EXPIRED,
        REFRESH_EXPIRED,
        UNAUTHORIZED,
        UNKNOWN
    }
    private StatusCode statusCode;
    public static TokenStatus of(StatusCode status){
        return makeTokenStatus(status);
    }
    private static TokenStatus makeTokenStatus(StatusCode status) {
        if (OK.equals(status)){
            return new TokenStatus(OK);
        }
        if (UNAUTHORIZED.equals(status)){
            return new TokenStatus(UNAUTHORIZED);
        }

        if (EXPIRED.equals(status)){
            return new TokenStatus(EXPIRED);
        }
        return new TokenStatus(UNKNOWN);
    }
}

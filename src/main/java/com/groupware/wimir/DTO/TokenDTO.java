package com.groupware.wimir.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


//토큰의 값을 헤더에서 뽑거나 헤더에서 삽입할때 쓰는 dto
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDTO {
    private String grantType;
    private String accessToken;     //
    private Long tokenExpiresIn;    //토큰만료시간
    //private String refreshToken;

}
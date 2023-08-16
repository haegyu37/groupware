package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Authority;
import lombok.*;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
@ToString
@Getter
@Setter
public class MemberBlockDTO {
    private Long id;
    private Authority authority;

}

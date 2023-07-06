package com.groupware.groupware.dto;

import com.groupware.groupware.entity.Member;
import lombok.*;


//Response를 보낼때 쓰이는 dto다.
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberResponseDto {
    private String no;
    private String name;
    private String position;
    private String part;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                //.email(member.getEmail())
                .no(member.getNo())
                .name(member.getName())
                .position(member.getPosition())
                .part(member.getPart())
                .build();
    }
}
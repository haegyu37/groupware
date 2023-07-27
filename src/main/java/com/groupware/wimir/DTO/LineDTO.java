package com.groupware.wimir.DTO;
import com.groupware.wimir.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor // 기본 생성자 추가
@AllArgsConstructor
@ToString
@Getter @Setter
public class LineDTO {

    private List<Long> approvers;
    private String name;
    private Long writer;

}
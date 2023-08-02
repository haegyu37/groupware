package com.groupware.wimir.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ApprovalDTO {

    private int status; //결재자별 결재상태 (1승인 2반려)
    private String reason;
    private Long document;


}

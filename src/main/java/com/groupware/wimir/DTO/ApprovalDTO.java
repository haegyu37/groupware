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

    private String status; //결재자별 결재상태 승인/반려
    private Long document;
}

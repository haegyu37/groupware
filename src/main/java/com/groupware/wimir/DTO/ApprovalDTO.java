package com.groupware.wimir.DTO;

//import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class ApprovalDTO {

        private Long documentId;
        private List<Long> approvers;
//        private String name;
        private LocalDateTime approvalDate;
        private int status; //결재자별 결재상태 (0결재전 1승인 2반려)
        private Long lineId;




}

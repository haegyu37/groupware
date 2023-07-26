package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class ApprovalDTO {

        private Document documentId;
        private List<Long> approverIds;
        private String name;
        private LocalDateTime approvalDate;
        private int approved;

}

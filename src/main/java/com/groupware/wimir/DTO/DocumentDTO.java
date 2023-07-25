package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Member;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class DocumentDTO {

    private String title;
    private String content;
//    private List<ApprovalDTO> approvers;
    private LocalDateTime createDate; //작성일
//    private LocalDateTime updateDate; //수정일
    private Member writer; // 작성자 (Member와 연관관계)
    private int status; // 1: 작성 상태, 0: 임시저장 상태
    private Approval approvalId;
//    private  Long dno;  // 문서 번호(디폴트 값은 0)
//    private  Long sno; // 문서 임시저장 번호(디폴트 값은 0)



}

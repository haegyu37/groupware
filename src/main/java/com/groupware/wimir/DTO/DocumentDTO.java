package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class DocumentDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate; //작성일
    private Member writer; // 작성자 (Member와 연관관계)
    private int status; // 1: 작성 상태, 0: 임시저장 상태
    private Approval approvalId;
    private Template template; // 양식명
    private Long tempNo;    // 양식별 문서번호

    public Template getTemplate() {
        return template;
    }

    public void setTemplate(Template template) { this.template = template; }


}

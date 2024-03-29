package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.TemplateRepository;
import com.groupware.wimir.service.TemplateService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DocumentDTO {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createDate; //작성일
    private Member writer; // 작성자 (Member와 연관관계)
    private int status; // 1: 작성 상태, 0: 임시저장 상태
    private Long template; //양식 아이디
    private List<Long> approvers; //결재라인 아이디
    private Long lineId; //즐겨찾기 결재라인 아이디
}

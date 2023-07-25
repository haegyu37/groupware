package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter @Setter
public class ApprovalDTO {

//    private Long docId; //문서 아이디
//
//    private Long lineId; //결재자
//    @Autowired
//    private String title;
//    @Autowired
//    private String content;
    private Long documentId;
    private Long id;
    private int Status;

}

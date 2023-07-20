package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@AllArgsConstructor
@ToString
@Getter @Setter
public class ApprovalDTO {

//    private Long docId; //문서 아이디
//
//    private Long lineId; //결재자
    @Autowired
    private List<Long> memberList;
    @Autowired
    private Document document;

}

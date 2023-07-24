package com.groupware.wimir.DTO;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.DTO.DocumentDTO;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;
@AllArgsConstructor
@ToString
@Getter @Setter
public class RequestBodyObject {

    @Autowired
    private DocumentDTO documentDTO;
    @Autowired
    private List<ApprovalDTO> approvalDTOs;

}

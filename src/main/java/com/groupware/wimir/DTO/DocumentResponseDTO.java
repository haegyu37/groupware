package com.groupware.wimir.DTO;

import com.groupware.wimir.entity.Document;
import lombok.*;

import java.util.List;
import java.util.Map;
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class DocumentResponseDTO {
    private Document document;
    private Map<Long, List<Map<String, Object>>> groupedApprovals;
    private Map<String, Object> appInfoForCancel;
}
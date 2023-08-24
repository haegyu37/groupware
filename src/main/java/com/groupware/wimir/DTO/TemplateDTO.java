package com.groupware.wimir.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TemplateDTO {
    private String category;
    private String content;

}


package com.groupware.wimir.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@ToString
@Getter @Setter

public class TemplateDTO {
    private Long id;
    private String name;
    private Date date;
    private String category;
}


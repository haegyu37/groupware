package com.groupware.wimir.dto;

import com.groupware.wimir.entity.Line;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@ToString
@Getter @Setter
public class DocumentDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Date writtenDate;
    private Long tem;
    private Line line;


}


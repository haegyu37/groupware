package com.groupware.wimir.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@ToString
@Getter @Setter


public class FileDTO {
    private Long id;
    private String name;
    private Long size;
    private String path;
    private Long documentId;

}

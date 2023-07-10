package com.groupware.wimir.dto;

import java.util.Date;

public class DocumentDTO {
    private Long id;
    private String title;
    private String content;
    private String writer;
    private Date writtenDate;
    private Long tem;

    public void setTem(Long tem) {
        this.tem = tem;
    }

    public Long getTem() {
        return tem;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}


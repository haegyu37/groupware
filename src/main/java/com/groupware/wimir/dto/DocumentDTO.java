<<<<<<< HEAD
//package com.groupware.wimir.dto;
//
//import java.util.Date;
//
//public class DocumentDTO {
//    private Long id;
//    private String title;
//    private String content;
//    private String writer;
//    private Date writtenDate;
//    private Long tem;
//
//    public void setTem(Long tem) {
//        this.tem = tem;
//    }
//
//    public Long getTem() {
//        return tem;
//    }
//
//    public String getWriter() {
//        return writer;
//    }
//
//    public void setWriter(String writer) {
//        this.writer = writer;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//}
//
=======
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

>>>>>>> d210e306cbd058c79f80ea7fb5090b2384db43f4

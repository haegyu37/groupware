//package com.groupware.wimir.entity;
//
//import lombok.*;
//
//import javax.persistence.*;
//
//@Entity
//@Table(name = "attachment")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Attachment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id; //첨부파일 아이디
//
//    private String name; //첨부파일 이름
//
//    private Long size; //첨부파일 크기
//
//    private String path; //첨부파일 경로
//
//    private Long docId; //첨부파일이 담긴 문서 아이디
//
//    private String writter; //문서작성자 아이디
//
//    @Transient
//    private String attachmentLocation;
//
//    public Attachment(String name, Long size, String path, Long docId, String writter) {
//        this.name = name;
//        this.size = size;
//        this.path = path;
//        this.docId = docId;
//        this.writter = writter;
//    }
//
//    public void setAttachmentLocation(String attachmentLocation) {
//        this.attachmentLocation = attachmentLocation;
//    }
//
//    public String getAttachmentLocation() {
//        return attachmentLocation;
//    }
//}

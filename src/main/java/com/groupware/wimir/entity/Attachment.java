package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //첨부파일 Id

    private String originalName; // 첨부파일 원본 이름

    private String savedName; // 첨부파일 저장 이름

    private Long size; // 첨부파일 크기

    private String path; // 첨부파일 경로

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "document_id")
    private Document document;  // 첨부파일은 문서 id 값에 할당함

    @Builder
    public Attachment(Long id, String originalName, String savedName, Long size, String path, Document document) {
        this.id = id;
        this.originalName = originalName;
        this.savedName = savedName;
        this.size = size;
        this.path = path;
        this.document = document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }
}
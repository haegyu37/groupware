package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "attachment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "path")
    private String path;

    @Column(name = "doc_id")
    private Long docId;

    @Column(name = "writter")
    private String writter;

    @Transient
    private String attachmentLocation;

    public Attachment(String name, Long size, String path, Long docId, String writter) {
        this.name = name;
        this.size = size;
        this.path = path;
        this.docId = docId;
        this.writter = writter;
    }

    public void setAttachmentLocation(String attachmentLocation) {
        this.attachmentLocation = attachmentLocation;
    }

    public String getAttachmentLocation() {
        return attachmentLocation;
    }
}

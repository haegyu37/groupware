package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="document")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private LocalDateTime createDate;

    @Column
    private LocalDateTime updateDate;

    @Column
    private Long memberId;

    @Column
    private Long temId;

    @Column
    private Long appId;

    @Builder
    public Document(Long id, String title, String content, LocalDateTime createDate,
                 LocalDateTime updateDate, Long memberId, Long temId, Long appId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.memberId = memberId;
        this.temId = temId;
        this.appId = appId;
    }

}

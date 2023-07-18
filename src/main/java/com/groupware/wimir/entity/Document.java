package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="document")
@Getter
@Setter
@NoArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //문서 아이디

    private String title; //문서명

    private String content; //문서내용

    private LocalDateTime createDate; //작성일

    private LocalDateTime updateDate; //수정일

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer; // 작성자 (Member와 연관관계)

    private Long temId; //양식

    private int status; // 1: 작성 상태, 0: 임시저장 상태

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long saveId;    // 임시저장 문서 ID

//    @ManyToMany
////    @JoinTable(
////            name = "document_approvers",
////            joinColumns = @JoinColumn(name = "document_id"),
////            inverseJoinColumns = @JoinColumn(name = "member_id")
////    )
//    private List<Long> approvers; // 결재자 정보
//
//    @ManyToMany
////    @JoinTable(
////            name = "document_viewers",
////            joinColumns = @JoinColumn(name = "document_id"),
////            inverseJoinColumns = @JoinColumn(name = "member_id")
////    )
//    private List<Long> viewers; // 참조자 정보


    @Builder
    public Document(Long id, String title, String content, LocalDateTime createDate,
                    LocalDateTime updateDate, Member writer, Long temId, int status, Long saveId) {

        this.id = id;
        this.title = title;
        this.content = content;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.writer = writer;
        this.temId = temId;
        this.status = status;
        this.saveId = saveId;
//        this.approvers = approvers;
//        this.viewers = viewers;
    }


//    public void addApprover(Member approver) {
//        if (approvers == null) {
//            approvers = new ArrayList<>();
//        }
//        approvers.add(approver.getId());
//    }
//
//    public void removeApprover(Member approver) {
//        if (approvers != null) {
//            approvers.remove(approver);
//        }
//    }
//
//    public void addViewer(Member viewer) {
//        if (viewers == null) {
//            viewers = new ArrayList<>();
//        }
//        viewers.add(viewer.getId());
//    }
//
//    public void removeViewer(Member viewer) {
//        if (viewers != null) {
//            viewers.remove(viewer);
//        }
//    }




}
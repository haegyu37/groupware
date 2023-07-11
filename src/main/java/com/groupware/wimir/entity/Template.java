package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "tem")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //양식 아이디

    @Column(name = "name")
    private String name; //양식명

    @Column(name = "date")
    private Date date; //양식 등록일

    @Column(name = "category")
    private String category; //양식 카테고리

}

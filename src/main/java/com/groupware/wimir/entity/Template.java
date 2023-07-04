package com.groupware.wimir.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tem")
@Getter
@Setter
@ToString
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //양식 아이디

    @Column
    private String name; //양식명

    @Column
    private Date date; //양식 등록일

    @Column
    private String category; //양식 카테고리

}

package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Part {

    @Id
    @GeneratedValue
    private Long id; //본부 아이디

    @Column
    private String name; //본부명

}

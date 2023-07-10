package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "part")
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //본부 아이디

    @Column(name = "name")
    private String name; //본부명

    @Column(name = "division")
    private String division; //식별자

}

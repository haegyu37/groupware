package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter @Setter
@Table(name = "position")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id; //직급 아이디

    @Column(name = "name")
    private String name; //직급명

}

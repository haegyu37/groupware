package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue
    private Long id; //팀 아이디

    @Column
    private String name; //팀명

    @OneToOne
    @JoinColumn(name="part_id")
    private Part part; //본부 아이디
}

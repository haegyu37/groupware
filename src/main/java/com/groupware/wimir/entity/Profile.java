package com.groupware.wimir.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
@Setter
@Table(name = "profile")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imgName;

    private String oriName;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updateProfile (String oriName, String imgName, String imgUrl){
        this.oriName = oriName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}

package com.groupware.wimir.DTO;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ProfileDTO {

    private Long id;
    private String imgName;
    private String oriName;
    private String imgUrl;
}

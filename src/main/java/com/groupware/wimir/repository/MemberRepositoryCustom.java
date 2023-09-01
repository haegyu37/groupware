package com.groupware.wimir.repository;

import com.groupware.wimir.DTO.MemberSerchDTO;
import com.groupware.wimir.entity.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MemberRepositoryCustom{
    List<Member> getMembers(MemberSerchDTO memberSerchDTO);

}

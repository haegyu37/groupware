package com.groupware.wimir.repository;

import com.groupware.wimir.DTO.MemberSerchDTO;
import com.groupware.wimir.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

//    private BooleanExpression serchSellStatusEq

    @Override
    public List<Member> getMembers(MemberSerchDTO memberSerchDTO) {
        return null;
    }
}

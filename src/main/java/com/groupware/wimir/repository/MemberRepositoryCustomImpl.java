package com.groupware.wimir.repository;

import com.groupware.wimir.DTO.MemberSerchDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.QMember;
import com.groupware.wimir.entity.Team;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.data.domain.PageImpl;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class MemberRepositoryCustomImpl implements MemberRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public MemberRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    private BooleanExpression searchSellStatusEq(Team team) {
        return team == null ? null : QMember.member.team.eq(team);
    }

    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        //이름이랑 사번으로 찾음
        if (StringUtils.equals("name", searchBy)) {
            return QMember.member.name.like("%" + searchQuery + "%");
        } else if (StringUtils.equals("createBy", searchBy)) {
            return QMember.member.no.like("%" + searchQuery + "%");
        }

        return null;
    }

    @Override
    public List<Member> getMembers(MemberSerchDTO memberSerchDTO) {
        QueryResults<Member> results = queryFactory
                .selectFrom(QMember.member)
                .where(searchSellStatusEq(memberSerchDTO.getTeam()),
                        searchByLike(memberSerchDTO.getSearchBy(),
                        memberSerchDTO.getSearchQuery()))
                .orderBy(QMember.member.id.desc())
                .fetchResults();

        List<Member> content = results.getResults();
//        long total = results.getTotal();
        return content;
    }
}

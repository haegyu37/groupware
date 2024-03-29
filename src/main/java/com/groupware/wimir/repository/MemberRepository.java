package com.groupware.wimir.repository;

import com.groupware.wimir.DTO.MemberSerchDTO;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;


import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom{

    Optional<Member> findByNo(String no);

    boolean existsByNo(String no);

    List<Member> findByTeam(Team team);

    List<Member> findByPosition(Position position);


}



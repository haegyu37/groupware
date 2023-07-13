package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByNo(String no);

    boolean existsByNo(String no);

    List<Member> findByPositionIn(String[] positions);


}
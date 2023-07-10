package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNo(String no);
    List<Member> findByTeam(Team team);

    boolean existsByNo(String no);
}

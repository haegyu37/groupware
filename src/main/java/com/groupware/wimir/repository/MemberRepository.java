package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.entity.Team;
import com.groupware.wimir.entity.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Optional;


import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByNo(String no);

    boolean existsByNo(String no);

    List<Member> findByTeam(Team team);

}



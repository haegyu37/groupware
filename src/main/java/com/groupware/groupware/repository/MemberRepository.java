package com.groupware.groupware.repository;

import com.groupware.groupware.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNo(String no);
    boolean existsByNo(String no);
}
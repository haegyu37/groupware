package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
    List<Profile> findByMember(Member member);
}

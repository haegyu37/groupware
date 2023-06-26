package com.groupware.groupware.repository;

import com.groupware.groupware.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    Optional<Users> findByUserId(String userId);

}

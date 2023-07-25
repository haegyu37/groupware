package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByStatus(int status);

    @Query("SELECT MAX(sno) FROM Document")
    Long findMaxSno();

    @Query("SELECT MAX(dno) FROM Document")
    Long findMaxDno();

    Page<Document> findByStatusNot(int status, Pageable pageable);

}
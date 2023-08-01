package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.entity.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    @Query("SELECT MAX(sno) FROM Document")
    Long findMaxSno();

    @Query("SELECT MAX(dno) FROM Document")
    Long findMaxDno();

    Page<Document> findByWriter(Member writer, Pageable pageable);

    Page<Document> findByStatusNot(int status, Pageable pageable);

    Page<Document> findByWriterIdAndStatus(Long memberId, int status, Pageable pageable);

    Page<Document> findByTemplateIdAndStatus(Long id, int status, Pageable pageable);

    Page<Document> findByWriterAndTemplateIdAndStatus(Long memberId, Long id, int status, Pageable pageable);

    @Query("SELECT COUNT(d) FROM Document d WHERE d.status = 1 AND d.template = :template AND d.id >= :id")
    Long countByTempNo(@Param("template") Template template, @Param("id") Long id);

}


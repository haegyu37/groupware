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

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    @Query("SELECT MAX(sno) FROM Document")
    Long findMaxSno();

    @Query("SELECT MAX(dno) FROM Document")
    Long findMaxDno();

    List<Document> findByStatusNot(int status);

    List<Document> findByWriterIdAndStatus(Long memberId, int status);

    List<Document> findByTemplateIdAndStatus(Long id, int status);

    List<Document> findByWriterAndTemplateIdAndStatus(Long memberId, Long id, int status);

    Optional<Document> findById(Long id);

    @Query("SELECT d FROM Document d JOIN FETCH d.template WHERE d.id = :documentId")
    Document findDocumentWithTemplateById(@Param("documentId") Long documentId);

    @Query("SELECT COALESCE(MAX(d.tempNo), 0) FROM Document d WHERE d.template = :template")
    Long findMaxTempNoByTemplate(@Param("template") Template template);

//    Document findByDno(Long dno);

    List<Document> findByWriterIdAndStatusAndResult(Long id, int status, String result);

    @Query("SELECT MAX(id) FROM Document")
    Long findMaxId();
}


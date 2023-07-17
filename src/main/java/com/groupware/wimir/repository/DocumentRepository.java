package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByStatus(int status);

    Document findBySaveId(Long saveId);

    void deleteBySaveId(Long saveId);
}

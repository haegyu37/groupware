package com.groupware.ahnkookyukyu.repository;

import com.groupware.ahnkookyukyu.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}

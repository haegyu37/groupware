package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findAll();

    List<Approval> findByWriter(Long writerId);

    @Query("SELECT MAX(lineId) FROM Approval")
    Long findMaxLineId();

    List<Approval> findByLineId(Long lineId);

    List<Approval> findByMemberId(Long id);

    List<Approval> findByDocument(Document document);

    void deleteByLineId(Long id);

    void deleteByDocument(Document document);
}

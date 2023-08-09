package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Approval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByName(String name);

    List<Approval> findAll();

    List<Approval> findByWriter(Long writerId);

    @Query("SELECT MAX(lineId) FROM Approval")
    Long findMaxLineId();

    List<Approval> findByLineId(Long lineId);

    List<Approval> findByMemberId(Long id);

    List<Approval> findByDocument(Long document);

    void deleteByLineId(Long id);

//    List<Approval> findBySno(Long sno);
}

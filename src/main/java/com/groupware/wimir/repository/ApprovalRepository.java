package com.groupware.wimir.repository;

import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {
    List<Approval> findByName(String name);

    List<Approval> findAll();

//    Page<Approval> findByWriterIdAndStatus(Long memberId, Pageable pageable);

//    Approval getLineByLineId(Long id);

    List<Approval> findByWriter(Long writerId);

    @Query("SELECT MAX(lineId) FROM Approval")
    Long findMaxLineId();

    List<Approval> findByLineId(Long lineId);


    List<Long> findMemberIdByLineId(Long id);
}

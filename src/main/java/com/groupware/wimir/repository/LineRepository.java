//package com.groupware.wimir.repository;
//
//import com.groupware.wimir.entity.ApprovalLine;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface LineRepository extends JpaRepository <ApprovalLine, Long> {
//
//    Page<ApprovalLine> findByWriterIdAndStatus(Long memberId, Pageable pageable);
//
//    Page<ApprovalLine> getApprovalLineByName(String name, Pageable pageable);
//
////    Page<ApprovalLine> getApprovalLineByName(String name);
//}

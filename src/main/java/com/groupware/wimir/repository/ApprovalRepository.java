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

@Repository
public interface ApprovalRepository extends JpaRepository<Approval, Long> {

    @Override
    List<Approval> findAll(); //Iterable 형식 말고 List 형식으로 받기


    Approval approval_Before(Member loginMember);
    Approval approval_Ing(Member loginMember);
    Approval approval_Done(Member loginMember);

    List<Approval> findByStatusAndDocumentIsNotNull(int status);
    List<Approval> findByApproverAndStatus(Member loginMember);


    List<Approval> selectRecentList(Member loginMember); //내 결재 목록
    List<Approval> selectRecentList1(Member loginMember); //내가 작성한 결재
    List<Approval> selectRecentList2(Member loginMember); //결재 수신 목록

    Page<Approval> selectApprovalList(Pageable pageable, String searchText);

    int listCount(String searchText);

    // 휴가신청서 등록
    int insertLetterOfApproval(Approval approval);
    int insertLetterOfApproval2(Approval approval);
    int insertLetterOfApproval3(Approval approval);

    Approval selectApprovalListDetail(int appNo);

    int rejectUpdate(Approval approval);

    int approved1(int appNo);
    int approved2(int appNo);
    int approved3(int appNo);

    // 휴가신청서 등록
    int insertApproval(Approval approval);
    int insertAppLeave(Approval approval);
    int insertReceiveRef(Approval approval);

    // 지출결의서 등록
    int insertExpenseReport(Approval approval);
    int insertExpenseReport2(Approval approval);
    int insertExpenseReport3(Approval approval);

    Approval selectExpenseReportListDetail(int appNo);

    Approval viewAppLeaveList(int appNo); //APP_LEAVE DAO

    Page<Approval> findByNameContaining(String searchText, Pageable pageable);

}

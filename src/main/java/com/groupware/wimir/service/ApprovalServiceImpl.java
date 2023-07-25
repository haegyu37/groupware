package com.groupware.wimir.service;

import com.groupware.wimir.DTO.ApprovalDTO;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovalServiceImpl implements ApprovalService{


    // 결재할 문서 목록을 조회하는 메서드
    public List<Approval> getApprovalListToApprove(Member loginMember) {
        // 여기서는 가정적으로 로그인한 멤버의 ID를 사용하여 해당 멤버가 결재자인 문서를 조회하도록 하였습니다.
        // 실제로는 결재자 정보를 바탕으로 조건을 추가하여 쿼리를 작성해야 합니다.

        // 예시: 로그인한 멤버의 ID를 사용하여 해당 멤버가 결재자인 문서를 조회하는 쿼리
        // List<Approval> approvalList = approvalRepository.findByMemberIdAndStatus(loginMember.getId(), 0);

        // 위의 쿼리가 실제로 사용되어야 하지만, 여기서는 가정적으로 더미 데이터를 생성하여 예시를 보여드리겠습니다.
        // 따라서 실제로는 데이터베이스 쿼리를 사용해야 합니다.
        // 아래 코드는 더미 데이터를 생성하여 결재 상태가 0인(대기 중인) 결재 문서를 조회하는 예시입니다.

        List<Approval> approvalList = approvalRepository.findByStatus(0);

        return approvalList;
    }

    private final ApprovalRepository approvalRepository;

    @Autowired
    public ApprovalServiceImpl(ApprovalRepository approverRepository) {
        this.approvalRepository = approverRepository;
    }


    @Override
    public int approval_Before(Member loginMember) {

        return approvalRepository.approval_Before(loginMember);
    }

    @Override
    public int approval_Ing(Member loginMember) {

        return approvalRepository.approval_Ing(loginMember);
    }

    @Override
    public int approval_Done(Member loginMember) {

        return approvalRepository.approval_Done(loginMember);
    }

    @Override
    public List<Approval> getRecentList(Member loginMember) {

        return approvalRepository.selectRecentList(loginMember);
    }

    @Override
    public List<Approval> getRecentList1(Member loginMember) {

        return approvalRepository.selectRecentList1(loginMember);
    }

    @Override
    public List<Approval> getRecentList2(Member loginMember) {

        return approvalRepository.selectRecentList2(loginMember);
    }

    @Override
    public Page<Approval> getApprovalList(Pageable pageable, String searchText) {
        return approvalRepository.findByNameContaining(searchText, pageable);
    }

    @Override
    public int getListCount(String searchText) {

        return approvalRepository.listCount(searchText);
    }
    @Override
    public int saveLetterOfApproval(Approval approval) {
        int result = 0;

        result = approvalRepository.insertLetterOfApproval(approval);

        return result;
    }

    @Override
    public int saveLetterOfApproval2(Approval approval) {
        int result = 0;

        result = approvalRepository.insertLetterOfApproval2(approval);

        return result;
    }

    @Override
    public int saveLetterOfApproval3(Approval approval) {
        int result = 0;

        result = approvalRepository.insertLetterOfApproval3(approval);

        return result;
    }

    @Override
    public Approval findListByNo(int appNo) {

        return approvalRepository.selectApprovalListDetail(appNo);
    }

    @Override
    public int rejectUpdate(Approval approval) {
        int result = 0;

        result = approvalRepository.rejectUpdate(approval);

        return result;
    }

    @Override
    public int approved1(int appNo) {
        int result = 0;

        result = approvalRepository.approved1(appNo);

        return result;
    }

    @Override
    public int approved2(int appNo) {
        int result = 0;

        result = approvalRepository.approved2(appNo);

        return result;
    }

    @Override
    public int approved3(int appNo) {
        int result = 0;

        result = approvalRepository.approved3(appNo);

        return result;
    }

    // 휴가신청서 등록
    @Override
    public int insertApproval(Approval approval) {

        return approvalRepository.insertApproval(approval);
    }

    @Override
    public int insertLeave(Approval approval) {

        return approvalRepository.insertAppLeave(approval);
    }

    @Override
    public int insertReceive(Approval approval) {

        return approvalRepository.insertReceiveRef(approval);
    }

    @Override
    public int saveExpenseReport(Approval approval) {
        int result = 0;

        result = approvalRepository.insertExpenseReport(approval);

        return result;
    }

    @Override
    public int saveExpenseReport2(Approval approval) {
        int result = 0;

        result = approvalRepository.insertExpenseReport2(approval);

        return result;
    }

    @Override
    public int saveExpenseReport3(Approval approval) {
        int result = 0;

        result = approvalRepository.insertExpenseReport3(approval);

        return result;
    }

    @Override
    public Approval findExpenseReportListByNo(int appNo) {

        return approvalRepository.selectExpenseReportListDetail(appNo);
    }

    // 휴가신청서 상세보기
    @Override
    public Approval findListByLeaveNo(int appNo) {
        System.out.println(appNo);

        return approvalRepository.viewAppLeaveList(appNo);
    }

}

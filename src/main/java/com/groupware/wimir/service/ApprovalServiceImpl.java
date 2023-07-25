//package com.groupware.wimir.service;
//
//import com.groupware.wimir.DTO.ApprovalDTO;
//import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.repository.ApprovalRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class ApprovalServiceImpl implements ApprovalService{
//
//    private final ApprovalRepository approvalRepository;
//
//    @Autowired
//    public ApprovalServiceImpl(ApprovalRepository approverRepository) {
//        this.approvalRepository = approverRepository;
//    }
//
//
//    @Override
//    public int approvalCount_YET(Member loginMember) {
//
//        return approvalRepository.approvalCount_YET(loginMember);
//    }
//
//    @Override
//    public int approvalCount_UNDER(Member loginMember) {
//
//        return approvalRepository.approvalCount_UNDER(loginMember);
//    }
//
//    @Override
//    public int approvalCount_DONE(Member loginMember) {
//
//        return approvalRepository.approvalCount_DONE(loginMember);
//    }
//
//    @Override
//    public List<Approval> getRecentList(Member loginMember) {
//
//        return approvalRepository.selectRecentList(loginMember);
//    }
//
//    @Override
//    public List<Approval> getRecentList1(Member loginMember) {
//
//        return approvalRepository.selectRecentList1(loginMember);
//    }
//
//    @Override
//    public List<Approval> getRecentList2(Member loginMember) {
//
//        return approvalRepository.selectRecentList2(loginMember);
//    }
//
////    @Override
////    public List<Approval> getApprovalList(PageInfo pageInfo, String searchText) {
////
////        int offset = (pageInfo.getCurrentPage() - 1) * pageInfo.getListLimit();
////        RowBounds rowBounds = new RowBounds(offset, pageInfo.getListLimit());
////
////        return approvalDao.selectApprovalList(rowBounds,searchText);
////    }
////
////    @Override
////    public int getListCount(String searchText) {
////
////        return approvalRepository.listCount(searchText);
////    }
////
////    @Override
////    public int saveLetterOfApproval(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.insertLetterOfApproval(approval);
////
////        return result;
////    }
////
////    @Override
////    public int saveLetterOfApproval2(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.insertLetterOfApproval2(approval);
////
////        return result;
////    }
////
////    @Override
////    public int saveLetterOfApproval3(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.insertLetterOfApproval3(approval);
////
////        return result;
////    }
////
////    @Override
////    public Approval findListByNo(int appNo) {
////
////        return approvalRepository.selectApprovalListDetail(appNo);
////    }
////
////    @Override
////    public int rejectUpdate(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.rejectUpdate(approval);
////
////        return result;
////    }
////
////    @Override
////    public int approved1(int appNo) {
////        int result = 0;
////
////        result = approvalRepository.approved1(appNo);
////
////        return result;
////    }
////
////    @Override
////    public int approved2(int appNo) {
////        int result = 0;
////
////        result = approvalRepository.approved2(appNo);
////
////        return result;
////    }
////
////    @Override
////    public int approved3(int appNo) {
////        int result = 0;
////
////        result = approvalRepository.approved3(appNo);
////
////        return result;
////    }
////
////    // 휴가신청서 등록
////    @Override
////    public int insertApproval(Approval approval) {
////
////        return approvalRepository.insertApproval(approval);
////    }
////
////    @Override
////    public int insertLeave(Approval approval) {
////
////        return approvalRepository.insertAppLeave(approval);
////    }
////
////    @Override
////    public int insertReceive(Approval approval) {
////
////        return approvalRepository.insertReceiveRef(approval);
////    }
////
////    @Override
////    public int saveExpenseReport(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.insertExpenseReport(approval);
////
////        return result;
////    }
////
////    @Override
////    public int saveExpenseReport2(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.insertExpenseReport2(approval);
////
////        return result;
////    }
////
////    @Override
////    public int saveExpenseReport3(Approval approval) {
////        int result = 0;
////
////        result = approvalRepository.insertExpenseReport3(approval);
////
////        return result;
////    }
////
////    @Override
////    public Approval findExpenseReportListByNo(int appNo) {
////
////        return approvalRepository.selectExpenseReportListDetail(appNo);
////    }
////
////    // 휴가신청서 상세보기
////    @Override
////    public Approval findListByLeaveNo(int appNo) {
////        System.out.println(appNo);
////
////        return approvalRepository.viewAppLeaveList(appNo);
////    }
//
//}

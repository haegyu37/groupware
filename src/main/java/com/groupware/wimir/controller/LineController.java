package com.groupware.wimir.controller;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.LineDTO;
import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.repository.ApprovalRepository;
//import com.groupware.wimir.repository.LineRepository;
import com.groupware.wimir.repository.MemberRepository;
import com.groupware.wimir.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/line")
@SessionAttributes("loginMember")
public class LineController {

    @Autowired
    private LineService lineService;
    @Autowired
    private ApprovalRepository approvalRepository;
    @Autowired
    private MemberRepository memberRepository;

    //마이페이지에서 결재라인 저장하기
    @PostMapping("/create")
    public ResponseEntity<String> saveApprovalLine(@RequestBody LineDTO lineDTO) {

        Long maxLineId = approvalRepository.findMaxLineId(); // DB에서 결재라인아이디의 최대값을 가져옴
        if(maxLineId == null) {
            maxLineId = 0L;
        } else {
            maxLineId = maxLineId + 1;
        }

        for (Long approverId :lineDTO.getApprovers()) { //List approvers 각각의 값을 approverId에 대입
            if (approverId != null) {

                //멤버가 존재하면 ..
                Approval approval = new Approval();
                approval.setMemberId(approverId);
                approval.setName(lineDTO.getName());
                approval.setWriter(SecurityUtil.getCurrentMemberId());
                approval.setCategory(lineDTO.getCategory());
                approval.setLineId(maxLineId);

//                if (maxLineId != null) {
//                    approval.setLineId(maxLineId);
//                } else {
//                    approval.setLineId(0L);
//                }

                approvalRepository.save(approval); //MemberId, Name, Writer값 각각 저장

            } else {
                // 존재하지 않는 멤버에 대한 예외 처리 로직
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("직원을 찾을 수 없습니다. " + approverId);
            }
        }

        return ResponseEntity.ok("결재라인을 저장했습니다. ");
    }

//    //모든 저장된 결재라인 불러오기
//    @GetMapping("/list")
//    public List<Approval> getAllLines() {
//        return approvalRepository.findAll();
//    }
//
//    //모든 저장된 결재라인 불러오기(토큰값으로 제한)
//    @GetMapping(value = "/mylist")
//    public Approval getMyLines() {
//        Long currentMemberId = SecurityUtil.getCurrentMemberId();
//        return lineService.findByWriter(currentMemberId);
//    }


    //모든 결재라인 불러오기
//    @GetMapping(value = "/list")
//    public List<Approval> groupApprovalsByLineId() {
//        return approvalRepository.findAll();
//    }

    @GetMapping(value = "/list")
    public Map<Long, List<Approval>> groupApprovalsByLineId() {
        List<Approval> allApprovals = approvalRepository.findAll();
        return Approval.groupByLineId(allApprovals);
    }

    @GetMapping(value = "/mylist")
    public Map<Long, List<Approval>> getMyLines() {
        Long currentMemberId = SecurityUtil.getCurrentMemberId();
        //id를 기준으로 Approval을 찾는 메소드
        List<Approval> myApproval = approvalRepository.findByWriter(currentMemberId);
        return Approval.groupByLineId(myApproval);

    }

    @GetMapping(value = "/{id}")
    public Map<Long, List<Approval>> getApprovalLineById(@PathVariable Long id) {
        return Approval.groupByLineId(lineService.getLineByLineId(id));

    }


//    //모든 결재라인 불러오기(토큰값으로 제한)
//    @GetMapping(value = "/mylist")
//    public List<Approval> getMyLines() {
//        Long currentMemberId = SecurityUtil.getCurrentMemberId();
//        return lineService.findByWriter(currentMemberId);
//    }
//
//    //저장한 결재라인을 lineid로 출력
//    @GetMapping("/{id}")
//    public List<Approval> getApprovalLineById(@PathVariable Long id) {
//        return lineService.getLineByLineId(id);
//    }






}

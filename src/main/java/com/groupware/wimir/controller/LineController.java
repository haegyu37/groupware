package com.groupware.wimir.controller;

import com.groupware.wimir.DTO.LineDTO;
import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.repository.LineRepository;
import com.groupware.wimir.service.LineService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/line")
@SessionAttributes("loginMember")
public class LineController {

    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private LineService lineService;

    //결재라인 저장하기
    @PostMapping("/create")
    public ResponseEntity<String> saveApprovalLine(@RequestBody LineDTO lineDTO) {
        List<Long> approvers = lineDTO.getApprovers(); //List<Long>으로 입력받은 값을 approvers에 대입
        String name = lineDTO.getName();

        for (Long approverId : approvers) { //List approvers 각각의 값을 approverId에 대입
            if (approverId != null) {
                ApprovalLine line = new ApprovalLine();
                line.setMemberId(approverId);
                line.setName(name);
                lineRepository.save(line); //MemberId, Name값 각각 저장
            } else {
                // 존재하지 않는 멤버에 대한 예외 처리 로직
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("직원을 찾을 수 없습니다. " + approverId);
            }
        }

        return ResponseEntity.ok("결재라인을 저장했습니다. ");
    }

    //모든 저장된 결재라인 불러오기
    @GetMapping("/list")
    public Map<String, List<ApprovalLine>> getAllApprovalLinesGroupedByName() {
        return lineService.groupLinesByName();
    }

    //저장한 결재라인을 결재이름별로 불러오기
    @GetMapping("/{name}")
    public ResponseEntity<?> getApprovalLineById(@PathVariable String name) {
        List<ApprovalLine> approvalLine = lineService.getApprovalLineByName(name);
        if (approvalLine != null) {
            return ResponseEntity.ok(approvalLine);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


}

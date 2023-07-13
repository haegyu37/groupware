package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.LineRepository;
import com.groupware.wimir.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LineService {

    @Autowired
    private LineRepository lineRepository;
    @Autowired
    private MemberRepository memberRepository;

    public void selectApproversForApprovalLine(List<Employee> organizationChart, List<String> selectedEmployees) {
        List<String> approvalLine = new ArrayList<>();

        // 선택된 결재자들의 이름을 결재라인에 추가
        for (String employeeName : selectedEmployees) {
            Employee selectedEmployee = findEmployeeByName(organizationChart, employeeName);
            if (selectedEmployee != null) {
                approvalLine.add(selectedEmployee.getName());
            }
        }

        // 결재라인을 저장하거나 필요한 처리를 수행
        saveApprovalLine(approvalLine);
    }


}


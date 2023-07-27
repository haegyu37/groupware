package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.entity.Approval;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.ApprovalRepository;
import com.groupware.wimir.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LineService {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ApprovalRepository approvalRepository;

    public Map<String, List<Approval>> groupAllLinesByNameTocken(Long userId, Pageable pageable) {
        List<Approval> allLines = approvalRepository.findAll();
        return allLines.stream()
                .filter(line -> line.getWriter().equals(userId))
                .collect(Collectors.groupingBy(Approval::getName));
    }

    public List<Approval> getApprovalLineByName(String name) {
        return approvalRepository.findByName(name);
    }

    public Map<String, List<Approval>> groupLinesByName() {
        List<Approval> allLines = approvalRepository.findAll();
        return allLines.stream().collect(Collectors.groupingBy(Approval::getName));
    }

}

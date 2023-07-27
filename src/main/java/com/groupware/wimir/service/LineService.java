package com.groupware.wimir.service;

import com.groupware.wimir.entity.ApprovalLine;
import com.groupware.wimir.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private LineRepository lineRepository;

    public List<ApprovalLine> findLinesByName(String name) {
        return lineRepository.findByName(name);
    }

    public List<ApprovalLine> findAllLines() {
        return lineRepository.findAll();
    }

    public Map<String, List<ApprovalLine>> groupLinesByName() {
        List<ApprovalLine> allLines = lineRepository.findAll();
        return allLines.stream().collect(Collectors.groupingBy(ApprovalLine::getName));
    }

    public List<ApprovalLine> getApprovalLineByName(String name) {
        return lineRepository.findByName(name);
    }
}

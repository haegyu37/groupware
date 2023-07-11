package com.groupware.wimir.service;

import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineService {

    @Autowired
    private LineRepository lineRepository;

//    @Autowired
//    public LineService(LineRepository lineRepository) {
//        this.lineRepository = lineRepository;
//    }

    public Line assignLineToDocument(Line line, Document document) {
        // 결재라인 정보를 사용하여 문서에 할당된 결재라인을 업데이트하는 로직을 구현합니다.
        Line assignedLine = document.getLine();
        if (assignedLine == null) {
            assignedLine = new Line();
        }
        assignedLine.setName(line.getName());
        assignedLine.setStep(line.getStep());
        assignedLine.setLineStatus(line.getLineStatus());
        assignedLine.setMember(line.getMember());
        assignedLine.setDocument(document);

        // 여기서 필요한 추가적인 로직을 구현합니다.
        // 예를 들어, 중복된 결재순서가 있는지 확인하거나 유효한 결재자인지 검증할 수 있습니다.

        return assignedLine;
    }

    public Line assignLineToMember(Line line, Member member) {
        // 멤버에 결재라인 할당 로직 구현
        Line assignedLine = member.getLine();
        if (assignedLine == null) {
            assignedLine = new Line();
        }
        assignedLine.setName(line.getName());
        assignedLine.setStep(line.getStep());
        assignedLine.setLineStatus(line.getLineStatus());
        assignedLine.setMember(member);

        // 필요한 추가 로직 구현

        member.setLine(assignedLine);
        return lineRepository.save(assignedLine);
    }
}

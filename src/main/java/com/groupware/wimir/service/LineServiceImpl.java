package com.groupware.wimir.service;

import com.groupware.wimir.entity.Line;
import com.groupware.wimir.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineServiceImpl implements LineService {

    private final LineRepository lineRepository;

    @Autowired
    public LineServiceImpl(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Override
    public List<Line> getAllLines() {
        return lineRepository.findAll();
    }

    @Override
    public Line createLine(Line line) {
        return lineRepository.save(line);
    }

    @Override
    public Line updateLine(Line line) {
        return lineRepository.save(line);
    }

    @Override
    public void deleteLine(Long lineId) {
        lineRepository.deleteById(lineId);
    }

    @Override
    public Line getLineById(Long lineId) {
        return lineRepository.findById(lineId).orElse(null);
    }
}

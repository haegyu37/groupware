package com.groupware.wimir.service;

import com.groupware.wimir.entity.Line;
import com.groupware.wimir.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LineServiceImpl extends LineService {

    private final LineRepository lineRepository;

    @Autowired
    public LineServiceImpl(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

}

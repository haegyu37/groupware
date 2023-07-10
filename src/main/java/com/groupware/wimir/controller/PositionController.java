package com.groupware.wimir.controller;

import com.groupware.wimir.entity.Position;
import com.groupware.wimir.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/position")
public class PositionController {

    @Autowired
    private final PositionRepository positionRepository;

    @Autowired
    public PositionController(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    @GetMapping
    public List<Position> getAllPositions() {
        return positionRepository.findAll();
    }
}
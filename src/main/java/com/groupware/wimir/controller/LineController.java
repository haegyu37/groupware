package com.groupware.wimir.controller;

import com.groupware.wimir.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/line")
public class LineController {

    @Autowired
    private LineService lineService;



}

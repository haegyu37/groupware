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



}


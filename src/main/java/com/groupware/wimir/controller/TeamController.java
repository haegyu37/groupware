//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.entity.Team;
//import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.repository.TeamRepository;
//import com.groupware.wimir.service.TeamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RequestMapping("/team")
//@RestController
//public class TeamController {
//
//    @Autowired
//    private TeamService teamService;
//
//    @GetMapping
//    public List<Team> getAllTeams() {
//        return teamService.getAllTeams();
//    }
//}

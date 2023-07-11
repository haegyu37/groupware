//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.entity.Part;
//import com.groupware.wimir.entity.Team;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.repository.PartRepository;
//import com.groupware.wimir.repository.TeamRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/part")
//public class PartController {
//
//    private final PartRepository partRepository;
//    private final TeamRepository teamRepository;
//    private final MemberRepository memberRepository;
//
//    @Autowired
//    public PartController(PartRepository partRepository, TeamRepository teamRepository, MemberRepository memberRepository) {
//        this.partRepository = partRepository;
//        this.teamRepository = teamRepository;
//        this.memberRepository = memberRepository;
//    }
//
//    // 본부 전체 조회
//    @GetMapping
//    public List<Part> getAllParts() {
//        return partRepository.findAll();
//    }
//
//    // 부서 조회
//    @GetMapping("/{partId}/team")
//    public List<Team> getTeamsByPartId(@PathVariable("partId") Long partId) {
//        Part part = partRepository.findById(partId).orElse(null);
//        if (part == null) {
//            // 본부를 찾을 수 없는 경우 처리 로직
//            return null;
//        }
//        return teamRepository.findByPart(part);
//    }
//
//    // 팀원 조회
//    @GetMapping("/{partId}/team/{teamId}")
//    public List<Member> getTeamMembers(@PathVariable("partId") Long partId, @PathVariable("teamId") Long teamId) {
//        Team team = teamRepository.findById(teamId).orElse(null);
//        if (team == null) {
//            // 팀을 찾을 수 없는 경우 처리 로직
//            return null;
//        }
//        return memberRepository.findByTeam(team);
//    }
//}

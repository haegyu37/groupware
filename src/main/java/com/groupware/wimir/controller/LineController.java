package com.groupware.wimir.controller;

import com.groupware.wimir.constant.Authority;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.service.LineService;
import com.groupware.wimir.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/line")
public class LineController {

    private final LineService lineService;
    private final MemberService memberService;

    @Autowired
    public LineController(LineService lineService, MemberService memberService) {
        this.lineService = lineService;
        this.memberService = memberService;
    }

//    /**
//     * 직원에게 결재라인을 지정합니다.
//     * 결재라인을 지정하면서 결재라인 이름을 지정할 수 있습니다.
//     * 직급이 낮을 수록 결재순서는 앞 순서입니다.
//     *
//     * @param line 결재라인 정보
//     * @return 지정된 결재라인이 적용된 직원 정보
//     */
    @PostMapping("/assign-line")
    public ResponseEntity<Member> assignLineToMember(@RequestBody Line line) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        // 현재 인증된 사용자의 정보를 가져옵니다.
        Member member = memberService.getMemberByUsername(name);
        //직원 중 일반계정만 결재라인으로 지정 가능
        if (member != null && member.getAuthority() != Authority.ROLE_BLOCK && member.getAuthority() != Authority.ROLE_ADMIN) {
            // 결재라인을 직원에게 할당합니다.
            Line assignedLine = lineService.assignLineToMember(line, member);
            member.setLine(assignedLine);
            // 직원 정보를 업데이트합니다.
            Member updatedMember = memberService.updateMember(member);
            return ResponseEntity.ok(updatedMember);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

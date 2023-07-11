package com.groupware.wimir.controller;

import com.groupware.wimir.dto.MemberRequestDTO;
import com.groupware.wimir.dto.MemberResponseDTO;
import com.groupware.wimir.dto.TokenDTO;
import com.groupware.wimir.entity.Part;
import com.groupware.wimir.entity.Position;
import com.groupware.wimir.entity.Team;
import com.groupware.wimir.repository.PartRepository;
import com.groupware.wimir.repository.PositionRepository;
import com.groupware.wimir.repository.TeamRepository;
import com.groupware.wimir.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private final AuthService authService;
    @Autowired
    private final PartRepository partRepository;
    @Autowired
    private final TeamRepository teamRepository;
    @Autowired
    private final PositionRepository positionRepository;


//    @PostMapping("/signup")
//    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
//        return ResponseEntity.ok(authService.signup(requestDto));
//    }

    @PostMapping("/admin/signup")
    public ResponseEntity<MemberResponseDTO> signup(@RequestBody MemberRequestDTO requestDto) {

        List<Part> parts = partRepository.findAll();
        List<Team> teams = teamRepository.findAll();
        List<Position> positions = positionRepository.findAll();

        MemberResponseDTO responseDto = authService.signup(requestDto);
        responseDto.setParts(parts);
        responseDto.setTeams(teams);
        responseDto.setPositions(positions);

        return ResponseEntity.ok(authService.signup(requestDto));
    }




    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody MemberRequestDTO requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
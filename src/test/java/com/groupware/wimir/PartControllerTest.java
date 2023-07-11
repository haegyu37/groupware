//package com.groupware.wimir;
//
//
//import com.groupware.wimir.controller.PartController;
//import com.groupware.wimir.entity.Part;
//import com.groupware.wimir.entity.Team;
//import com.groupware.wimir.repository.PartRepository;
//import com.groupware.wimir.repository.TeamRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.mockito.ArgumentMatchers.anyLong;
//import static org.mockito.Mockito.when;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(MockitoExtension.class)
//@WebMvcTest(PartController.class)
//public class PartControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private PartRepository partRepository;
//
//    @MockBean
//    private TeamRepository teamRepository;
//
//    @Test
//    public void testGetPartById() throws Exception {
//        Long partId = 1L;
//        Part part = new Part();
//        part.setId(partId);
//        part.setName("Sample Part");
//
//        when(partRepository.findById(anyLong())).thenReturn(Optional.of(part));
//
//        mockMvc.perform(get("/part/{partId}", partId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(partId))
//                .andExpect(jsonPath("$.name").value("Sample Part"));
//    }
//
//    @Test
//    public void testGetTeamsByPartId() throws Exception {
//        Long partId = 1L;
//        Part part = new Part();
//        part.setId(partId);
//        part.setName("Sample Part");
//
//        Team team1 = new Team();
//        team1.setId(1L);
//        team1.setName("Team 1");
//        team1.setPart(part);
//
//        Team team2 = new Team();
//        team2.setId(2L);
//        team2.setName("Team 2");
//        team2.setPart(part);
//
//        List<Team> teams = new ArrayList<>();
//        teams.add(team1);
//        teams.add(team2);
//
//        when(partRepository.findById(anyLong())).thenReturn(Optional.of(part));
//        when(teamRepository.findByPart(part)).thenReturn(teams);
//
//        mockMvc.perform(get("/part/{partId}/teams", partId))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1L))
//                .andExpect(jsonPath("$[0].name").value("Team 1"))
//                .andExpect(jsonPath("$[1].id").value(2L))
//                .andExpect(jsonPath("$[1].name").value("Team 2"));
//    }
//}

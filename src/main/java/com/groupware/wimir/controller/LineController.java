//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.constant.LineStatus;
//import com.groupware.wimir.entity.Line;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.service.LineService;
//import com.groupware.wimir.service.MemberService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/lines")
//public class LineController {
//
//    private final LineService lineService;
//    private final MemberService memberService;
//
//    @Autowired
//    public LineController(LineService lineService, MemberService memberService) {
//        this.lineService = lineService;
//        this.memberService = memberService;
//    }
//
//    @GetMapping
//    public ResponseEntity<List<Line>> getAllLines() {
//        List<Line> lines = lineService.getAllLines();
//        return new ResponseEntity<>(lines, HttpStatus.OK);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<Line> getLineById(@PathVariable("id") Long id) {
//        Line line = lineService.getLineById(id);
//        if (line != null) {
//            return new ResponseEntity<>(line, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<Line> createLine(@RequestParam("name") String name,
//                                           @RequestParam("step") int step,
//                                           @RequestParam("lineStatus") LineStatus lineStatus,
//                                           @RequestParam("userId") Long userId) {
//        Member selectedMember = memberService.getMemberById(userId);
//        if (selectedMember != null) {
//            Line line = new Line();
//            line.setName(name);
//            line.setStep(step);
//            line.setLineStatus(lineStatus);
//            line.setMember(selectedMember);
//
//            Line createdLine = lineService.createLine(line);
//            return new ResponseEntity<>(createdLine, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}

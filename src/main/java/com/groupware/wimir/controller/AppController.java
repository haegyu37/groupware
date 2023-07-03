package com.groupware.wimir.controller;

import com.groupware.wimir.dto.DocumentDTO;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.service.AppService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.http.HttpRequest;
import java.util.List;

@RestController
@RequestMapping("/approval")
public class AppController {
    private final AppService appService;

    public AppController(AppService appService) {
        this.appService = appService;
    }

    // 결재 생성
    @PostMapping
    public ResponseEntity<App> createApproval(@RequestBody App app) {
        App savedApp = appService.saveApproval(app);
        return ResponseEntity.ok(savedApp);
    }

    // 결재 수정
    @PutMapping("/{id}")
    public ResponseEntity<App> updateApproval(@PathVariable Long id, @RequestBody App updatedApp) {
        App app = appService.getApprovalById(id);

        app.setDocId(updatedApp.getDocId());
        app.setLineId(updatedApp.getLineId());
        app.setAppStatus(updatedApp.getAppStatus());

        App savedApp = appService.saveApproval(app);
        return ResponseEntity.ok(savedApp);
    }

    // 모든 결재 조회
    @GetMapping("/all")
    public ResponseEntity<List<App>> getAllApprovals() {
        List<App> approvals = appService.getAllApprovals();
        return ResponseEntity.ok(approvals);
    }

    // 특정 결재 조회
    @GetMapping("/{id}")
    public ResponseEntity<App> getApprovalById(@PathVariable Long id) {
        App approval = appService.getApprovalById(id);
        return ResponseEntity.ok(approval);
    }

    // 결재 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        appService.deleteApproval(id);
        return ResponseEntity.noContent().build();
    }

}

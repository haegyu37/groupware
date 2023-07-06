package com.groupware.wimir.controller;

import com.groupware.wimir.dto.DocumentDTO;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.exception.ResourceNotFoundException;
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
import java.util.Optional;

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
        Optional<App> optionalApproval = appService.getApprovalById(id);

        if (optionalApproval.isPresent()) {
            App app = optionalApproval.get();

            app.setDoc(updatedApp.getDoc());
            app.setLine(updatedApp.getLine());
            app.setAppStatus(updatedApp.getAppStatus());

            App savedApp = appService.saveApproval(app);
            return ResponseEntity.ok(savedApp);
        } else {
            throw new ResourceNotFoundException("The document with the given ID does not exist.");
        }
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
        Optional<App> optionalApproval = appService.getApprovalById(id);

        if (optionalApproval.isPresent()) {
            App approval = optionalApproval.get();
            return ResponseEntity.ok(approval);
        } else {
            throw new ResourceNotFoundException("The document with the given ID does not exist.");
        }
    }

    // 결재 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApproval(@PathVariable Long id) {
        appService.deleteApproval(id);
        return ResponseEntity.noContent().build();
    }

}

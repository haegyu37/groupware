package com.groupware.wimir.service;

import com.groupware.wimir.constant.AppStatus;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.AppRepository;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.LineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final DocumentRepository documentRepository;
    private final LineRepository lineRepository;

    // 결재 생성 또는 업데이트
    public App saveApproval(App app) {
        return appRepository.save(app);
    }

    // 모든 결재 조회
    public List<App> getAllApprovals() {
        return appRepository.findAll();
    }

    // 특정 ID의 결재 조회

    public Optional<App> getApprovalById(Long id) {
        return appRepository.findById(id);
    }

    // 결재 삭제
    public void deleteApproval(Long id) {
        appRepository.deleteById(id);
    }

    // 문서 결재
    public void approveDocument(Long appId) {
        Optional<App> optionalApp = appRepository.findById(appId); // 수정된 부분

        if (optionalApp.isPresent()) {
            App app = optionalApp.get();

            AppStatus currentStatus = app.getAppStatus();

            if (currentStatus == AppStatus.BEFORE) {
                app.setAppStatus(currentStatus.approve()); // 결재 상태를 승인으로 변경
                appRepository.save(app);

                Line line = app.getLine();
                int nextStep = line.getStep() + 1;
                Line nextApprover = lineRepository.findByStep(nextStep);

                if (nextApprover == null) {
                    // 다음 결재자가 없는 경우 처리 로직
                    app.setAppStatus(AppStatus.PASSED); // 모든 결재자 승인 완료 상태로 변경
                    appRepository.save(app);
                } else {
                    // 다음 결재자가 있는 경우 처리 로직
                    app.setLine(nextApprover); // 다음 결재자 설정
                    documentRepository.save(app.getDoc());
                }
            } else if (currentStatus == AppStatus.APPROVING) {
                throw new IllegalStateException("The document is already being approved.");
            } else {
                throw new IllegalStateException("The document is not in a valid status for approval.");
            }

        } else {
            throw new IllegalStateException("The document with the given ID does not exist.");
        }
    }

    // 문서 반려
    public void returnedDocument(Long appId) {
        Optional<App> optionalApp = appRepository.findById(appId); // 수정된 부분

        if (optionalApp.isPresent()) {
            App app = optionalApp.get();

            AppStatus currentStatus = app.getAppStatus();

            if (currentStatus == AppStatus.APPROVING) {
                app.setAppStatus(currentStatus.reject()); // 결재 상태를 반려로 변경
                appRepository.save(app);
            } else {
                throw new IllegalStateException("The document is not in a valid status for rejection.");
            }
        } else {
            throw new IllegalStateException("The document with the given ID does not exist.");
        }
    }
}
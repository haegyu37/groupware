package com.groupware.wimir.service;

import com.groupware.wimir.constant.AppStatus;
import com.groupware.wimir.dto.AppDTO;
import com.groupware.wimir.entity.App;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Line;
import com.groupware.wimir.exception.ResourceNotFoundException;
import com.groupware.wimir.repository.AppRepository;
import com.groupware.wimir.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppService {

    private final AppRepository appRepository;
    private final DocumentRepository documentRepository;
    private final Line line;


    // 결재 생성 또는 업데이트
    public App saveApproval(App app) {
        return appRepository.save(app);
    }

    // 모든 결재 조회
    public List<App> getAllApprovals() {
        return appRepository.findAll();
    }

    // 특정 ID의 결재 조회
    public App getApprovalById(Long id) {
        return appRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("App", "id", id)); //-> 여기서 오류가 생긴단다
    }

    // 결재 삭제
    public void deleteApproval(Long id) {
        appRepository.deleteById(id);
    }

    public void approveDocument(Long appId) {
        App app = getApprovalById(appId);
        if (app.getAppStatus() == AppStatus.approving) {
            // 결재 상태를 승인으로 변경
            app.setAppStatus(AppStatus.approved);
            appRepository.save(app);

            // 다음 결재자에게 문서를 넘기기
            int nextStep = line.getStep() + 1; // 다음 결재자(Step)로 넘김
            Document document = documentRepository.findById(app.getDocId())
                    .orElseThrow(() -> new ResourceNotFoundException("Document", "id", app.getDocId()));
            line.setStep(nextStep); // 다음 결재자 ID 설정
            documentRepository.save(document);
        } else {
            throw new IllegalStateException("The document is not in pending status for approval.");
        }
    }

    // 문서 반려
    public void returnedDocument(Long appId) {
        App app = getApprovalById(appId);
        if (app.getAppStatus() == AppStatus.approving) {
            // 결재 상태를 반려로 변경
            app.setAppStatus(AppStatus.returned);
            appRepository.save(app);
        } else {
            throw new IllegalStateException("The document is not in pending status for approval.");
        }
    }
}

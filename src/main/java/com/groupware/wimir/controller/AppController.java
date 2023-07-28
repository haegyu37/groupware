//package com.groupware.wimir.controller;
//
//import com.groupware.wimir.service.ApprovalService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//public class AppController {
//    @RestController
//    @RequestMapping("/approval")
//    public class ApprovalController {
//
//        @Autowired
//        private ApprovalService approvalService;
//
////        // 결재 라인 지정 API
////        @PostMapping("/create-line")
////        public ResponseEntity<?> createApprovalLine(@RequestBody ApprovalLineRequestDTO requestDTO) {
////            try {
////                // 요청된 정보를 바탕으로 결재 라인 생성
////                ApprovalLine approvalLine = approvalService.createApprovalLine(requestDTO);
////                return ResponseEntity.ok(approvalLine);
////            } catch (Exception e) {
////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create approval line.");
////            }
////        }
////
////        // 문서와 함께 결재 요청 API
////        @PostMapping("/submit")
////        public ResponseEntity<?> submitApproval(@RequestBody DocumentSubmissionRequestDTO requestDTO) {
//////            try {
////                // 결재 요청과 관련된 처리 로직
//////            approvalService.submitApproval(requestDTO)
//////            @Override
//////            public void submitApproval(ApprovalRequestDTO requestDTO) {
//////                Long documentId = requestDTO.getDocumentId();
//////                Long approverId = requestDTO.getApproverId();
//////                boolean approved = requestDTO.isApproved();
//////
//////                // 여기서 결재 처리 로직을 구현하면 됩니다.
//////                // 예를 들어, 결재자 ID로 사용자를 찾고 결재 여부에 따라 문서의 상태를 업데이트하거나 다음 단계로 전달하는 등의 작업을 수행합니다.
//////                // 이 예시에서는 단순히 메시지를 출력하는 것으로 대체합니다.
//////
//////                String approvalStatus = approved ? "승인" : "반려";
//////                System.out.println(approverId + "가 문서 ID " + documentId + "에 대해 " + approvalStatus + " 처리했습니다.");
//////            }
////                boolean isApproved = approvalService.submitApproval(requestDTO);
////                if (isApproved) {
////                    return ResponseEntity.ok("Approval request submitted successfully.");
////                } else {
////                    return ResponseEntity.ok("Approval request submitted, waiting for approval.");
////                }
//////            } catch (Exception e) {
//////                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit approval request.");
//////            }
////        }
//
//        // 결재 문서 승인 API
//        @PostMapping("/approve")
//        public ResponseEntity<?> approveDocument(@RequestBody ApprovalRequestDTO requestDTO) {
//            try {
//                // 결재 승인과 관련된 처리 로직
//                boolean isApproved = approvalService.approveDocument(requestDTO);
//                if (isApproved) {
//                    return ResponseEntity.ok("Document approved successfully.");
//                } else {
//                    return ResponseEntity.ok("Document approval in progress.");
//                }
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to approve the document.");
//            }
//        }
//
//        // 결재 문서 반려 API
//        @PostMapping("/reject")
//        public ResponseEntity<?> rejectDocument(@RequestBody ApprovalRequestDTO requestDTO) {
//            try {
//                // 결재 반려와 관련된 처리 로직
//                boolean isRejected = approvalService.rejectDocument(requestDTO);
//                if (isRejected) {
//                    return ResponseEntity.ok("Document rejected successfully.");
//                } else {
//                    return ResponseEntity.ok("Document rejection in progress.");
//                }
//            } catch (Exception e) {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reject the document.");
//            }
//        }
//    }
//
//}

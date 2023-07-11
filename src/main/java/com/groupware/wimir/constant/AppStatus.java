//package com.groupware.wimir.constant;
//
//public enum AppStatus {
//
//    BEFORE, APPROVING, APPROVED, RETURNED, PASSED;
//
//    // 결재 승인 처리
//    public AppStatus approve() {
//        if (this == BEFORE) {
//            return APPROVING;
//        } else if (this == APPROVING) {
//            return APPROVED;
//        } else {
//            throw new IllegalStateException("Invalid state transition: " + this.name() + " to APPROVED");
//        }
//    }
//
//    // 결재 반려 처리
//    public AppStatus reject() {
//        if (this == APPROVING) {
//            return RETURNED;
//        } else {
//            throw new IllegalStateException("Invalid state transition: " + this.name() + " to RETURNED");
//        }
//    }
//}

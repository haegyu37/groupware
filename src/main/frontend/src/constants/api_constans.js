// 토큰관련 API
export const LOGIN_API = "/auth/login"  // 로그인
export const LOGOUT_API = "/auth/logout" // 로그아웃
export const RENEW_TOKEN_API = "/auth/refresh"  // 토큰갱신

// 계정관련 API
export const CREATE_ID_API = "/admin/signup"   // 계정등록
export const MY_INFO_CHANGE_API = "/member/edit"  //TODO 본인 비밀번호 + 사진 변경
export const ACCOUNT_INFO_API ="/admin" //(+id) GET : 직원상세정보
export const ACCOUNT_EDIT_API ="/admin/members/edit"  //TODO (+id/password) POST : 관리자 -> 임직원 정보 변경
export const ACCOUNT_DELETE_API ="/admin/delete" // (+id) DELETE : 계정삭제
export const ACCOUNT_BLOCK_API="/admin/members/block" // POST : 계정 접속차단

// 문서관련 API
export const RECEIVE_DOCUMENT_LIST_API= "/approval/list"  // 수신문서함(결재라인에 내가 포함되어 있는 문서)
export const STANDBY_APPROVAL_LIST_API = "/approval/listnow" // 내차례 결재문서
export const TEMP_DOCUMENT_LIST_API = "/documents/savelist" // 임시저장함
export const ALL_DOCUMENT_LIST_API = "/admin/listdone" // 모든 결재 완료 문서
export const DOCUMENT_CREATE_API="/documents/create"    // 문서작성
export const DOCUMENT_UPDATE_API ="/documents/update"   //TODO (+id) 문서수정
export const DOCUMENT_READ_API ="/documents"   // (+id) 문서 상세보기
export const TEMP_DOCUMENT_READ_API ="/documents/save"   // (+id) 임시저장문서 상세보기
export const DOCUMENT_DELETE_API="/documents/delete"    // 문서 삭제
export const REPORT_DOCUMENT_LIST_API = "/documents/mylist" // 상신문서함
export const ONGOING_DOCUMENT_LIST_API ="/documents/mylist/ing" // 상신문서함 - 진행중
export const APPROVED_DOCUMENT_LIST_API ="/documents/mylist/approved" // 상신문서함 - 결재완료
export const REJECTED_DOCUMENT_LIST_API="/documents/mylist/rejected" // 상신문서함 - 반려

// 문서양식 API
export const CATEGORY_CREATE_API ="/admin/templates/create" // 양식 생성
export const CATEGORY_DELETE_API = "/admin/templates/delete"  // (+id) 양식 삭제
export const CATEGORY_LIST_API = "/templates/list"  // 양식 리스트 조회
export  const SHOW_CATEGORY_API="/templates" // (+id) 양식 데이터 조회
export const UPDATE_CATEGORY_API ="/admin/templates/edit" // (+id) 양식수정

// 결재관련 API
export const APPROVAL_SIGN_API ="/approval/approve" // 결재 승인 & 반려
export const APPROVAL_BOOKMARK_CREATE_API= "/line/create" // 결재라인 즐겨찾기 추가
export const APPROVAL_BOOKMARK_DELETE_API="/line/delete" // (+id) 결재라인 즐겨찾기 삭제
export const APPROVAL_BOOKMARK_LIST_API="/line/list"  // 결재라인 즐겨찾기 리스트
export const APPROVAL_BOOKMARK_INFO_API="/line" //(+id) 즐겨찾기 결재라인 상세
export const APPROVAL_CANCEL_API="/approval/cancel" // 결재 취소
export const APPROVAL_RECALL_API="/approval/back"   // 문서 회수 (작성자 ONLY)

// 정보조회 API
export const MY_INFO_API = "/member/me" //내정보 (o)
export const TEAM_INFO_API = "/approval/team" //팀 리스트 (o)
export const TEAM_MEMBER_INFO_API ="/approval/team" //팀별 팀원리스트 (o)
export const POSITION_INFO_API = "/approval/position" // 직급 리스트 (o)
export const MEMBER_LIST_INFO_API ="/admin/members" // 전직원 리스트 (o)

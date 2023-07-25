//package com.groupware.wimir.controller;
//
//import com.google.protobuf.Message;
//import com.groupware.wimir.DTO.ApprovalDTO;
//import com.groupware.wimir.entity.Approval;
//import com.groupware.wimir.entity.Member;
//import com.groupware.wimir.entity.Position;
//import com.groupware.wimir.entity.Team;
//import com.groupware.wimir.repository.MemberRepository;
//import com.groupware.wimir.service.ApprovalService;
//import com.groupware.wimir.service.DocumentService;
//import com.groupware.wimir.service.DocumentServiceImpl;
//import com.groupware.wimir.service.MemberService;
//import com.mysql.cj.xdevapi.JsonArray;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//import java.io.File;
//import java.io.IOException;
//import java.net.URLEncoder;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//@Slf4j
//@RestController
//@RequestMapping("/approval")
//@SessionAttributes("loginMember")
//public class ApprovalController {
//
//    @Autowired
//    private MemberRepository memberRepository;
//    @Autowired
//    private ApprovalService approvalService;
//
//    @Autowired
//    private ApprovalService service;
//
//    @Autowired
//    private MemberService memberService;
//
//    @Autowired
//    private DocumentServiceImpl documentService;
//
//    //팀 모두 출력
//    @GetMapping("/team")
//    public List<String> getAllTeam() {
//        List<String> teamNames = new ArrayList<>();
//        Team[] teams = Team.values();
//        for (Team team : teams) {
//            teamNames.add(team.name());
//        }
//        return teamNames;
//    }
//
//    //직급 모두 출력
//    @GetMapping("/position")
//    public List<String> getAllPosition() {
//        List<String> positionNames = new ArrayList<>();
//        Position[] positions = Position.values();
//        for (Position position : positions) {
//            positionNames.add(position.name());
//        }
//        return positionNames;
//    }
//
//    //팀원 출력
//    @GetMapping("/team/{team}")
//    public List<Member> getTeamMembers(@PathVariable("team") Team team) {
//
//        return memberRepository.findByTeam(team);
//    }
//
//    //직급원 출력
//    @GetMapping("/position/{position}")
//    public List<Member> getPositionMembers(@PathVariable("position") Position position) {
//
//        return memberRepository.findByPosition(position);
//    }
//
//    //결재 메인화면: 결재할 문서, 결재 중 문서, 결재 완료한 문서 출력
//    @GetMapping
//    public ModelAndView approvalMain(@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
//        ModelAndView model = new ModelAndView("approval/approvalMain");
//
//        int approval_Before = approvalService.approval_Before(loginMember); //결재할 문서
//        int approval_Ing = approvalService.approval_Ing(loginMember); //결재 중 문서
//        int approval_Done = approvalService.approval_Done(loginMember); //결재 완료한 문서
//
//        List<Approval> appMine = approvalService.getRecentList(loginMember); //내 결재 목록
//        List<Approval> appWrite = approvalService.getRecentList1(loginMember); //내가 작성한 결재
//        List<Approval> appReceive = approvalService.getRecentList2(loginMember); //결재 수신목록
//
//        model.addObject("mainList", appMine);
//        model.addObject("mainList1", appWrite);
//        model.addObject("mainList2", appReceive);
//        model.addObject("countYet", approval_Before);
//        model.addObject("countUnder", approval_Ing);
//        model.addObject("countDone", approval_Done);
//
//        return model;
//    }
//
//    //결재리스트
//    @GetMapping(value = "/approvalList")
//    public String approvalList(Model model,
//                               @RequestParam(value = "page", required = false, defaultValue = "1") int page,
//                               @RequestParam(value = "listLimit", required = false, defaultValue = "10") int listLimit,
//                               @RequestParam(value = "notice_search", required = false) String searchText) {
//
//        int listCount = approvalService.getListCount(searchText);
//
//        Pageable pageable = PageRequest.of(page - 1, listLimit);
//        Page<Approval> mainList2 = approvalService.getApprovalList(pageable, searchText);
//
//        System.out.println("mainList2 : " + mainList2.getContent());
//
//        if (searchText != null) {
//            model.addAttribute("notice_search", searchText);
//        }
//
//        model.addAttribute("mainList", mainList2.getContent());
//        model.addAttribute("pageInfo", mainList2);
//
//        return "approval/approvalList";
//    }
//
//
////    /** 수신참조자 모달 내 멤버 리스트 불러오기 (letterOfApproval) */
//
//    @GetMapping("/letterOfApproval")
//    public ModelAndView letterOfApproval(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                                         ModelAndView model, Member member) {
//
//        System.out.println("loginMember : " + loginMember);
//
//        Date date = new Date();
//        // DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT,
//        // DateFormat.SHORT, locale);
//
//        // String formattedDate = dateFormat.format(date);
//
//        SimpleDateFormat format = new SimpleDateFormat("yyyy 년 MM 월 dd 일");
//
//        String today = format.format(date);
//
//        model.addObject("serverTime", today);
//
//        List<Member> memberList = null;
//
//        memberList = memberService.selectMemberAllForApproval(loginMember.getId());
//
//        System.out.println("memberList : " + memberList);
//
//        model.addObject("memberList", memberList);
//        model.setViewName("approval/letterOfApproval");
//
//        return model;
//    }
//
//    @PostMapping("/letterOfApproval")
//    public ModelAndView letterOfApprovalWrite(Approval approval, ModelAndView model,  HttpServletRequest request,
//                                              @SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                                              @RequestParam("appLoaFileUpload") MultipartFile upfile) {
//
//        int result = 0;
//        int result2 = 0;
//        int result3 = 0;
//
//        approval.setAppWriterNo(loginMember.getUser_no());
//
////        // 품의서 파일업로드 // 살려야한다...
////        if(upfile != null && !upfile.isEmpty()) {
////
////            String renameFileName = saveAppFile(upfile, request);
////
////            System.out.println("renameFileName : " + renameFileName);
////
////            if(renameFileName != null) {
////                approval.setAppOriginalFileName(upfile.getOriginalFilename());
////                approval.setAppRenameFileName(renameFileName);
////
////                System.out.println("imgOriname : " + approval.getAppOriginalFileName() + " / imgRename : " + approval.getAppRenameFileName());
////            }
////        }
//
//        result = service.saveLetterOfApproval(approval);
//
//        approval.setLoaAppNo(approval.getAppNo());
//
//        result2 = service.saveLetterOfApproval2(approval);
//
//        approval.setReceiveRefAppNo(approval.getAppNo());
//
//        result3 = service.saveLetterOfApproval3(approval);
//
//        if (result > 0 && result2 > 0 && result3 > 0) {
//            model.addObject("msg", "품의서가 정상적으로 등록되었습니다.");
//            model.addObject("location", "/approval/approvalList");
//        } else {
//            model.addObject("msg", "품의서 등록에 실패하였습니다.");
//            model.addObject("location", "/");
//        }
//        model.setViewName("common/msg");
//        return model;
//    }
//
//    @GetMapping("/letterOfApprovalView")
//    public ModelAndView letterOfApprovalView(@RequestParam("appNo") int appNo, ModelAndView model) {
//        Approval approval = service.findListByNo(appNo);
//
//        System.out.println(approval);
//
//        model.addObject("approval", approval);
//        model.setViewName("/approval/letterOfApprovalView");
//
//        return model;
//    }
//
//    /** 수신참조자 모달 내 검색 */
//
//    @ResponseBody
//    @GetMapping("/searchMemberInModal")
//    public List<Member> searchMemberInModal(
//            @SessionAttribute(name = "loginMember", required = false) Member loginMember, Member member,
//            @RequestParam(value = "searchData") String searchData) {
//
//        System.out.println("searchData : " + searchData);
//
//        List<Member> memberList = null;
//
//        System.out.println("loginMember.getUser_id() : " + loginMember.getId());
//
//        memberList = memberService.selectSearchedMemberForApproval(searchData, loginMember.getId());
//
//        return memberList;
//    }
//
//    @ResponseBody
//    @PostMapping("/letterOfApprovalUpdate")
//    public int letterOfApprovalUpdate(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                                      Approval approval, @RequestParam(value = "rejectReasonText") String rejectReasonText,
//                                      @RequestParam(value = "appNo") Long appId) {
//        int result = 0;
//
//        approval.setId(appId);
//        approval.setReason(rejectReasonText);
//
//        System.out.println("appNo : " + appId);
//        System.out.println("rejectReasonText : " + rejectReasonText);
//        System.out.println("approval.getId() : " + approval.getId());
//        System.out.println("approval.getReason() : " + approval.getReason());
//
//        if (rejectReasonText != null) {
//            if (approval.getId() != 0) {
//                result = service.rejectUpdate(approval);
//                System.out.println("result: " + result);
//            }
//        } else {
//
//        }
//
//        return result;
//    }
//
//    // 자동완성
////    @ResponseBody
////    @GetMapping("/search/json")
////    public String searchJson(@RequestParam(value = "userName") String userName) {
////        System.out.println(userName);
////        List<Member> memSearch = memberService.getSearchMember(userName);
////
////        JsonArray array = new JsonArray();
////        for(int i=0; i < memSearch.size(); i++) {
////            array.add(memSearch.get(i).getName() + "_" + memSearch.get(i).getPosition() + "_" +memSearch.get(i).getTeam());
////        }
////
////        System.out.println(array);
////
////        Gson gson = new Gson();
////
////        return gson.toJson(array);
////    }
//
//    @ResponseBody
//    @PostMapping("/loaApproved1")
//    public int loaApproved1(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                            @RequestParam(value = "appNo") int appNo) {
//        int result = 0;
//        result = service.approved1(appNo);
//
//        return result;
//    }
//
//    @ResponseBody
//    @PostMapping("/loaApproved2")
//    public int loaApproved2(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                            @RequestParam(value = "appNo") int appNo) {
//        int result = 0;
//        result = service.approved2(appNo);
//
//        return result;
//    }
//
//    //쪽지보내기
////    @ResponseBody
////    @PostMapping("/loaApproved3")
////    public int loaApproved3(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
////                            @RequestParam(value = "appNo") int appNo, Message msg) {
////        int result = 0;
////        result = service.approved3(appNo);
////
////        // 쪽지보내기 기능
////        Approval approval = service.findByAppNoMsg(appNo);
////
////        if(approval.getAppPresent().equals("D")) {
////
////            String[] arr = approval.getReferList().split(", ");
////
////            for(int num1=0; num1<arr.length; num1++ ) {
////                System.out.println(arr[num1]);
////            }
////
////            int msgResult = 0;
////            List<Message> message = new ArrayList<Message>();
////
////            String msgContent = approval.getAppKinds() + "가 결재완료되었습니다.";
////
////            for(int i=0; i <arr.length; i++) {
////                Message forMsg = new Message();
////
////                String[] name = arr[i].split("_");
////
////                Member member = projectService.findReceiver(name[0], name[1], name[2]);
////
////                forMsg.setMsgFrom(loginMember.getUser_no());
////                forMsg.setMsgTo(member.getUser_no());
////                forMsg.setMsgContent(msgContent);
////                forMsg.setRank(name[1]);
////                forMsg.setDeptName(name[2]);
////
////                message.add(i,forMsg);
////
////            }
////
////            Map<String, Object> map = new HashMap<>();
////            map.put("list", message);
////
////            msgResult = projectService.sendProjectMsg(map);
////
////            if(msgResult > 0) {
////                System.out.println("전송완료");
////            } else {
////                System.out.println("전송실패");
////            }
////        }
////
////        return result;
////    }
//
////    // 휴가신청서
////    @RequestMapping("/leaveApplication")
////    public String leaveApplication(@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
////
////        return "/approval/leaveApplication";
////    }
//
//    @PostMapping("/updateLeave")
//    public ModelAndView insertLeave(ModelAndView model, HttpServletRequest request,
//                                    @SessionAttribute(name = "loginMember", required = false) Member loginMember, Approval approval) {
//
//        log.info("휴가 신청서 작성 컨트롤러 : " + approval);
//        int result = 0;
//        int result2 = 0;
//        int result3 = 0;
//
//        approval.setAppWriterNo(loginMember.getUser_no());
//
//        System.out.println(approval.getAppWriterNo() + "\n" + approval + "\n" + approval);
//        if (loginMember.getUser_no() == approval.getAppWriterNo()) {
//            System.out.println(loginMember.getUser_no() + " ,\n" + approval.getAppWriterNo());
//            result = service.insertApproval(approval);
//
//            approval.setLeaveAppNo(approval.getAppNo());
//            approval.setReceiveRefAppNo(approval.getAppNo());
//
//            System.out.println("approval.getReceiveRefAppNo() : " + approval.getReceiveRefAppNo());
//
//            result2 = service.insertLeave(approval);
//            result3 = service.insertReceive(approval);
//
////			System.out.println("97번줄 : " + appLeave.getLeaveAppNo());
////			System.out.println("101 result : " + result + "\nresult2 : " + result2);
//
//            if (result > 0 && result2 > 0 && result3 > 0) {
//                model.addObject("msg", "휴가신청서가 정상적으로 등록되었습니다.");
//                model.addObject("location", "/approval/approvalList");
//            } else {
//                model.addObject("msg", "결재서류 등록을 실패하였습니다.");
//                model.addObject("location", "/approval/leaveApplication");
//            }
//        }
//
//        model.setViewName("common/msg");
//
//        return model;
//    }
//
//    /** 수신참조자 모달 내 멤버 리스트 불러오기 (leaveApplication) */
//
//    @GetMapping("/leaveApplication")
//    public ModelAndView leaveApplication(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                                         ModelAndView model, Member member) {
//
//        System.out.println("loginMember : " + loginMember);
//
//        List<Member> memberList = null;
//
//        memberList = service2.selectMemberAllForApproval(loginMember.getUser_id());
//
//        System.out.println("memberList : " + memberList);
//
//        model.addObject("memberList", memberList);
//        model.setViewName("approval/leaveApplication");
//
//        return model;
//    }
//
//    /** 수신참조자 모달 내 멤버 리스트 불러오기 (expenseReport) */
//
//    @GetMapping("/expenseReport")
//    public ModelAndView expenseReport(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                                      ModelAndView model, Member member) {
//
//        System.out.println("loginMember : " + loginMember);
//
//        List<Member> memberList = null;
//
//        memberList = service2.selectMemberAllForApproval(loginMember.getUser_id());
//
//        System.out.println("memberList : " + memberList);
//
//        model.addObject("memberList", memberList);
//        model.setViewName("approval/expenseReport");
//
//        return model;
//    }
//
//    @PostMapping("/expenseReport")
//    public ModelAndView expenseReportWrite(@SessionAttribute(name = "loginMember", required = false) Member loginMember,
//                                           Approval approval, ModelAndView model) {
//        int result = 0;
//        int result2 = 0;
//        int result3 = 0;
//
//        approval.setAppWriterNo(loginMember.getUser_no());
//
//        result = service.saveExpenseReport(approval);
//
//        approval.setErAppNo(approval.getAppNo());
//
//        result2 = service.saveExpenseReport2(approval);
//
//        approval.setReceiveRefAppNo(approval.getAppNo());
//
//        result3 = service.saveExpenseReport3(approval);
//
//        if (result > 0 && result2 > 0 && result3 > 0) {
//            model.addObject("msg", "지출결의서가 정상적으로 등록되었습니다.");
//            model.addObject("location", "/approval/approvalList");
//        } else {
//            model.addObject("msg", "지출결의서 등록에 실패하였습니다.");
//            model.addObject("location", "/");
//        }
//
//        model.setViewName("common/msg");
//
//        return model;
//    }
//
//    @GetMapping("/expenseReportView")
//    public ModelAndView expenseReportView(@RequestParam("appNo") int appNo, ModelAndView model) {
//
//        Approval approval = service.findExpenseReportListByNo(appNo);
//        System.out.println("expenseReportView : " + approval);
//
//
//        model.addObject("approval", approval);
//        model.setViewName("/approval/expenseReportView");
//
//        return model;
//    }
//
//    @GetMapping("/leaveApplicationView")
//    public ModelAndView leaveApplicationView(@RequestParam("appNo") int appNo, ModelAndView model) {
//
//        Approval approval = service.findListByLeaveNo(appNo);
//
////	       System.out.println("507번줄 : " + approval);
//
//        model.addObject("approval", approval);
//        model.setViewName("/approval/leaveApplicationView");
//
//        return model;
//    }
//
//    // 파일첨부 관련
////    private String saveAppFile(MultipartFile file, HttpServletRequest request) {
////        String renamePath = null;
////        String originalFileName = null;
////        String renameFileName = null;
////        String rootPath = request.getSession().getServletContext().getRealPath("resources");
////        String savePath = rootPath + "/upload/approvalFile";
////
////        log.info("Root Path : " + rootPath);
////        log.info("Save Path : " + savePath);
////
////        File folder = new File(savePath); // Save Path가 실제로 존재하지 않으면 폴더를 생성하는 로직
////
////        if(!folder.exists()) {
////            folder.mkdirs();
////        }
////
////        originalFileName = file.getOriginalFilename();
////        renameFileName =
////                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS")) +
////                        originalFileName.substring(originalFileName.lastIndexOf("."));
////        renamePath = savePath + "/" + renameFileName;
////
////        try {
////            file.transferTo(new File(renamePath)); // 업로드 한 파일 데이터를 지정한 파일에 저장한다.
////        }catch (IOException e) {
////            System.out.println("파일 전송 에러 : " + e.getMessage());
////            e.printStackTrace();
////        }
////
////        return renameFileName;
////    }
////
////    @GetMapping("/appFileDown")
////    public ResponseEntity<Resource> fileDown(
////            @RequestParam("oriname") String oriname, @RequestParam("rename") String rename,
////            @RequestHeader(name="user-agent") String header) {
////
////        try {
////            Resource resource = resourceLoader.getResource("resources/upload/approvalFile/" + rename);
////            File file = resource.getFile();
////            boolean isMSIE = header.indexOf("Trident")!=-1||header.indexOf("MSIE")!=-1;
////            String encodeRename = "";
////
////            if(isMSIE) {
////                encodeRename = URLEncoder.encode(oriname, "UTF-8");
////                encodeRename = encodeRename.replaceAll("\\+", "%20");
////            }else {
////                encodeRename = new String(oriname.getBytes("UTF-8"),"ISO-8859-1");
////            }
////
////            return ResponseEntity.ok()
////                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=\"" + encodeRename + "\"")
////                    .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()))
////                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM.toString())
////                    .body(resource);
////        } catch (IOException e) {
////            e.printStackTrace();
////
////            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
////        }
////    }
//}

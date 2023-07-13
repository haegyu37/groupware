//package com.groupware.wimir.controller;
//
//
//import com.groupware.wimir.DTO.DocumentDTO;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//    @RequestMapping
//    @RestController
//    public class AppController {
//
////        private final AppRepository appRepository;
////        private final LineRepository lineRepository;
////        private final DocumentRepository documentRepository;
////
////        @Autowired
////        public AppController(AppRepository appRepository, LineRepository lineRepository, DocumentRepository documentRepository) {
////            this.appRepository = appRepository;
////            this.lineRepository = lineRepository;
////            this.documentRepository = documentRepository;
////        }
////
////        @PostMapping("/create-document")
////        public ResponseEntity<String> createDocument(@RequestBody DocumentDTO documentDTO) {
////            // 문서 생성 및 저장
////            Document document = new Document();
////            document.setTitle(documentDTO.getTitle());
////            document.setContent(documentDTO.getContent());
////            documentRepository.save(document);
////
////            // 결재 라인 선택
////            Line line = lineRepository.findById(documentDTO.getLine().getId())
////                    .orElseThrow(() -> new IllegalArgumentException("Invalid line ID"));
////            line.setDocument(document);
////            lineRepository.save(line);
////
////            // 결재 상태 초기화
////            App app = new App();
////            app.setAppStatus(AppStatus.BEFORE);
////            app.setDoc(document);
////            app.setLine(line);
////            appRepository.save(app);
////
//////            app.updateStatus();
////            appRepository.save(app);
////
////            return ResponseEntity.ok("Document created and approval line selected successfully.");
////        }
//    }
//

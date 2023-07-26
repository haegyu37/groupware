package com.groupware.wimir.service;

import com.groupware.wimir.Config.SecurityUtil;
import com.groupware.wimir.DTO.MemberResponseDTO;
import com.groupware.wimir.entity.Document;
import com.groupware.wimir.entity.Member;
import com.groupware.wimir.repository.DocumentRepository;
import com.groupware.wimir.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Override
    public Document findDocumentById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }

    @Override
    public Document saveDocument(Document document) {
        if (document.getStatus() == 0) {
            // 임시저장 상태인 경우 id는 null
            document.setDno(null);
        }
        return documentRepository.save(document);
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }

    @Override
    public Page<Document> findDocumentListByStatusNot(int status, Pageable pageable) {
        return documentRepository.findByStatusNot(status, pageable);
    }

    @Override
    public void setWriterByToken(Document document) {

        // 멤버 아이디를 이용하여 데이터베이스에서 멤버 정보를 조회합니다.
        Member writer = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다." ));

        // 문서의 작성자를 설정합니다.
        document.setWriter(writer);
    }

    @Override
    public Page<Document> findDocumentListByWriterAndStatus(Long memberId, int status, Pageable pageable) {
        return documentRepository.findByWriterIdAndStatus(memberId, status, pageable);
    }

//    public Document getDocumentById(Long documentId) {
//        // findById 메서드는 Optional<Document>를 반환한다고 가정합니다.
//        Document document = documentRepository.findById(documentId);
//        return document;
//
////        // 문서가 찾아지지 않은 경우를 처리할 수 있습니다.
////        // 여기에서는 orElseThrow를 사용하여 문서를 찾지 못한 경우 예외를 던집니다.
////        return optionalDocument.orElseThrow(() -> new RuntimeException("ID " + documentId + "에 해당하는 문서를 찾을 수 없습니다."));
//    }








}
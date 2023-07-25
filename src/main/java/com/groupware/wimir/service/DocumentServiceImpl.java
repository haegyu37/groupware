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
    public List<Document> findSaveDocumentList() {
        return documentRepository.findByStatus(0);
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
//        Document document, String token
        // 토큰 파싱 및 멤버 정보 조회 로직을 구현합니다.
        // 이 부분은 토큰 라이브러리나 Spring Security 등을 사용하여 구현할 수 있습니다.
        // 토큰을 파싱하여 멤버 아이디를 얻어온다고 가정하겠습니다.

//        memberRepository.findById(SecurityUtil.getCurrentMemberId())
//                .map(MemberResponseDTO::of)
//                .orElseThrow(() -> new RuntimeException("로그인 유저 정보가 없습니다"));


//        Long memberId = parseTokenAndGetMemberId(token);

        // 멤버 아이디를 이용하여 데이터베이스에서 멤버 정보를 조회합니다.
        Member writer = memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new RuntimeException("작성자를 찾을 수 없습니다." ));

        // 문서의 작성자를 설정합니다.
        document.setWriter(writer);
    }


}
import React, { useEffect } from 'react';

function DocumentComponent() {
    useEffect(() => {
        // 문서 생성
        fetch('http://localhost:8080/api/documents', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                title: '문서 제목',
                content: '문서 내용',
                writer: '작성자'
            })
        })
            .then(response => response.json())
            .then(data => {
                // 문서 생성 성공 시 처리 로직
                console.log('문서가 작성되었습니다.', data);
            })
            .catch(error => {
                // 오류 처리 로직
                console.error('문서 생성 중 오류가 발생했습니다.', error);
            });

        // 문서 조회
        fetch('http://localhost:8080/api/documents')
            .then(response => response.json())
            .then(data => {
                // 문서 조회 성공 시 처리 로직
                console.log('조회된 문서 목록:', data);
            })
            .catch(error => {
                // 오류 처리 로직
                console.error('문서 조회 중 오류가 발생했습니다.', error);
            });

        // 문서 수정
        fetch('http://localhost:8080/api/documents/{id}')
            .then(response => response.json())
            .then(data => {
                // 문서 조회 성공 시 처리 로직
                console.log('조회된 문서:', data);
            })
            .catch(error => {
                // 오류 처리 로직
                console.error('문서 조회 중 오류가 발생했습니다.', error);
            });

        // 문서 삭제
        fetch('http://localhost:8080/api/documents/{id}', {
            method: 'DELETE'
        })
            .then(() => {
                // 문서 삭제 성공 시 처리 로직
                console.log('문서가 삭제되었습니다.');
            })
            .catch(error => {
                // 오류 처리 로직
                console.error('문서 삭제 중 오류가 발생했습니다.', error);
            });
    }, []);

    return <div>Document Component</div>;

}

export default DocumentComponent;
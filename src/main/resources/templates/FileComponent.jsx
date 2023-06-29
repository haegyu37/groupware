import React, { useState } from 'react';

function FileComponent() {
    const [file, setFile] = useState(null);

    // 첨부파일 업로드
    const handleFileUpload = async (event) => {
        const selectedFile = event.target.files[0];

        const formData = new FormData();
        formData.append('file', selectedFile);
        formData.append('documentId', 123); // documentId를 적절한 값으로 변경

        try {
            const response = await fetch('/files', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                console.log('첨부파일이 업로드되었습니다.');
            } else {
                console.error('첨부파일 업로드를 실패했습니다.');
            }
        } catch (error) {
            console.error('첨부파일 업로드 중 오류가 발생했습니다.', error);
        }
    };

    // 첨부파일 다운로드
    const handleFileDownload = async () => {
        try {
            const response = await fetch('/files/123', {
                method: 'GET',
            });

            if (response.ok) {
                const blob = await response.blob();
                const url = URL.createObjectURL(blob);

                // 다운로드 링크 생성
                const link = document.createElement('a');
                link.href = url;
                link.download = 'filename.ext'; // 다운로드될 파일명과 확장자
                link.click();
            } else if (response.status === 404) {
                console.error('첨부파일을 찾을 수 없습니다.');
            } else {
                console.error('첨부파일 다운로드를 실패했습니다.');
            }
        } catch (error) {
            console.error('첨부파일 다운로드 중 오류가 발생했습니다.', error);
        }
    };

    // 첨부파일 삭제
    const handleFileDelete = async () => {
        try {
            const response = await fetch('/files/123', {
                method: 'DELETE',
            });

            if (response.ok) {
                console.log('첨부파일이 삭제되었습니다.');
            } else {
                console.error('첨부파일 삭제를 실패했습니다.');
            }
        } catch (error) {
            console.error('첨부파일 삭제 중 오류가 발생했습니다.', error);
        }
    };

    return (
        <div>
            <input type="file" onChange={handleFileUpload} />
            <button onClick={handleFileDownload}>Download</button>
            <button onClick={handleFileDelete}>Delete</button>
        </div>
    );
}

export default FileComponent;

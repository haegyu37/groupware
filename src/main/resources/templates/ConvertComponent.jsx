import React from 'react';

const ConvertComponent = () => {
    const uploadAndConvertFile = async (file) => {
        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch('/convert', {
                method: 'POST',
                body: formData,
            });

            if (response.ok) {
                console.log('파일 업로드 및 변환 성공');
            } else {
                console.error('파일 업로드 및 변환 실패');
            }
        } catch (error) {
            console.error('파일 업로드 및 변환 중 오류 발생', error);
        }
    };

    const handleFileSelect = (event) => {
        const selectedFile = event.target.files[0];
        uploadAndConvertFile(selectedFile);
    };

    return (
        <div>
            <input type="file" id="file-input" onChange={handleFileSelect} />
        </div>
    );
};

export default ConvertComponent;

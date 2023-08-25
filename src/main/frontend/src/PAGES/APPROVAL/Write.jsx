import styles from "./Write.module.css"
import Dropdown from 'react-bootstrap/Dropdown';
import {Button} from "react-bootstrap";
import {useEffect, useRef, useState} from "react";
import fetcher from "../../fetcher";
import {CATEGORY_LIST_API, DOCUMENT_CREATE_API, SHOW_CATEGORY_API} from "../../constants/api_constans";
import ApprovalPathModal from "./ApprovalPathModal";
import useStore from "../../store";
import {useNavigate} from "react-router-dom";
import {REPORT_DOCUMENT_COMPONENT, TEMP_DOCUMENT_COMPONENT} from "../../constants/component_constants";
import WriteSignTable from "./WriteSignTable";
import {CKEditor} from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import Swal from "sweetalert2";


function Write() {
    const {signLine} = useStore(state => state)
    const navigate = useNavigate();
    const approvers = [signLine.signTurn1.id, signLine.signTurn2.id, signLine.signRefer ? signLine.signRefer.id : null];

    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")
    const [category, setCategory] = useState("")
    const [categoryId, setCategoryId] = useState("")
    const [categoryList, setCategoryList] = useState([])
    const [showApprovalPathModal, setShowApprovalPathModal] = useState(false);

    useEffect(() => {
        fetcher.get(CATEGORY_LIST_API)
            .then((res) => setCategoryList(res.data))
    }, [])

    const saveBtn = (status) => {
        return fetcher.post(DOCUMENT_CREATE_API, {
            title: title,
            content: content,
            approvers: approvers,
            template: categoryId,
            status: status,
        })
    }
    const selectCategory = (id) => {
        fetcher.get(`${SHOW_CATEGORY_API}/${id}`)
            .then((res) => {
                setCategoryId(res.data?.id)
                setCategory(res.data?.category)
                setContent(res.data?.content)
            })
    }
    /** 저장, 임시저장 구분*/
    const handleTempSave = () => {
        saveBtn(0).then(() => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: '임시저장 완료',
                showConfirmButton: false,
                timer: 1500
            })
            navigate(`/page/${TEMP_DOCUMENT_COMPONENT}`)
        })
    }
    const handleSave = () => {
        if (!category) {
            Swal.fire({
                title: "문서양식을 선택하세요",
                icon: 'warning',
            })
            return;
        }
        const isEmpty = approvers.every(item => item === '');
        if (isEmpty) {
            Swal.fire({
                title: "결재라인을 입력하세요",
                icon: 'warning',
            })
            return;
        }
        saveBtn(1).then(() => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: '문서 상신 완료',
                showConfirmButton: false,
                timer: 1500
            })
            navigate(`/page/${REPORT_DOCUMENT_COMPONENT}`)
        })
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.upperContainer}>
                <div className={styles.select}>
                    <Dropdown>
                        <Dropdown.Toggle className="button">
                            {category ? category : "문서양식"}
                        </Dropdown.Toggle>
                        <Dropdown.Menu className={styles.dropMenu}>
                            {categoryList?.map((data) => {
                                return (
                                    <Dropdown.Item key={data.id} onClick={() => selectCategory(data.id)}>
                                        {data.category}
                                    </Dropdown.Item>
                                )
                            })}
                        </Dropdown.Menu>
                    </Dropdown>
                    <Button className="button" onClick={() => setShowApprovalPathModal(true)}>결재라인</Button>
                </div>

                <div className={styles.buttonGroup}>
                    <Button className="button" onClick={() => navigate(-1)}>작성취소</Button>
                    <Button className="button" onClick={handleTempSave}>임시저장</Button>
                    <Button className="button" onClick={handleSave}>상신하기</Button>
                </div>
            </div>

            <div className={styles.divisionLine}></div>
            <div className={styles.lowerContainer}>
                <div className={styles.categoryTitle}>
                    <p>{category ? category : "양식을 선택하세요"}</p>
                </div>

                <WriteSignTable/>

                <div className={styles.editorContainer}>
                    <div className={styles.documentTitle}>
                        <p>제목 :</p>
                        <input value={title}
                               placeholder={"제목을 입력하세요"}
                               style={{margin: "10px 0"}}
                               onChange={(e) => setTitle(e.target.value)}/>
                    </div>

                    <CKEditor
                        editor={ClassicEditor}
                        data={content}
                        config={{placeholder: "양식을 입력 하세요."}}
                        onChange={(event, editor) => {
                            const data = editor.getData();
                            setContent(data)
                        }}
                    />
                </div>
            </div>

            <ApprovalPathModal showApprovalPathModal={showApprovalPathModal}
                               handleApprovalPathModalClose={() => setShowApprovalPathModal(false)}
            />
        </div>
    )
}

export default Write
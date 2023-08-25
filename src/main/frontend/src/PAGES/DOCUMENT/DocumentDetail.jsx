import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {
    APPROVAL_CANCEL_API,
    APPROVAL_RECALL_API,
    APPROVAL_SIGN_API,
    DOCUMENT_DELETE_API,
    DOCUMENT_READ_API
} from "../../constants/api_constans";
import {useNavigate, useParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import styles from "../APPROVAL/Write.module.css"
import DocumentSignTable from "./components/DocumentSignTable";
import parse from 'html-react-parser';
import Swal from "sweetalert2";
import useStore from "../../store";
import {TEMP_DOCUMENT_COMPONENT} from "../../constants/component_constants";

function DocumentDetail() {
    const {id} = useParams();
    const navigate = useNavigate();
    const [documentData, setDocumentData] = useState({});
    const [signLine, setSignLine] = useState([]);
    const [approvalStatus, setApprovalStatus] = useState();
    const [isCompleted, setIsCompleted] = useState(false);
    const {myAccount} = useStore(state => state)

    useEffect(() => {
        fetchDocumentInfo()
    }, [id])

    const isStandby = documentData?.result === "결재대기"
    const isDone = documentData?.result === "승인" || documentData?.result === "반려"
    const isWriter = documentData?.writer?.no === myAccount.no
    const isMyTurn = approvalStatus?.myCurrent === "Y"
    const nextTurnNotSign = approvalStatus?.nextStauts !== "승인" && approvalStatus?.nextStauts !== "반려"

    const fetchDocumentInfo = () => {
        return fetcher.get(`${DOCUMENT_READ_API}/${id}`)
            .then((res) => {
                const {document, groupedApprovals, appInfoForCancel} = res.data
                console.log(res.data)
                // 문서 정보
                setDocumentData(document)
                // 결재라인
                setSignLine(groupedApprovals[document.dno])
                // 결재상태
                setApprovalStatus(appInfoForCancel)
                setIsCompleted(true)
            })
    }

    const approvalBtn = (status) => {
        Swal.fire({
            title: status === "승인" ? "결재를 승인하시겠습니까?" : "결재를 반려하시겠습니까?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                fetcher.post(APPROVAL_SIGN_API, {
                    document: documentData.id,
                    status: status
                })
                    .then(() => {
                        Swal.fire({
                            position: 'mid',
                            icon: 'success',
                            title: '완료',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        fetchDocumentInfo()
                    })
            }
        })
    }
    const cancelBtn = () => {
        Swal.fire({
            title: "결재를 취소하시겠습니까?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                fetcher.post(APPROVAL_CANCEL_API, {
                    document: documentData.id
                })
                    .then(() => {
                        Swal.fire({
                            position: 'mid',
                            icon: 'success',
                            title: '완료',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        fetchDocumentInfo()
                    })
            }
        })
    }

    const recallBtn = () => {
        fetcher.post(APPROVAL_RECALL_API, {
            document: documentData.id
        })
            .then(() => {
                Swal.fire({
                    position: 'mid',
                    icon: 'success',
                    title: '임시저장으로 변경되었습니다.',
                    showConfirmButton: false,
                    timer: 1500
                })
                navigate(`/page/${TEMP_DOCUMENT_COMPONENT}`)
            })
    }

    const deleteBtn = () => {
        if (!(isStandby)) {
            Swal.fire({
                title: "결재가 진행중인 문서입니다.",
                icon: 'warning',
            })
            return;
        }
        Swal.fire({
            title: "문서를 삭제 하시겠습니까?",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                fetcher.delete(`${DOCUMENT_DELETE_API}/${id}`)
                    .then(() => {
                        Swal.fire({
                            position: 'mid',
                            icon: 'success',
                            title: '삭제 완료',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        navigate(-1)
                    })
            }
        })
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.upperContainer}>
                <div className={styles.select}>
                    {isStandby && isWriter ? <Button className="button">문서수정</Button> : ""}
                    {isStandby && isWriter ? <Button className="button" onClick={deleteBtn}>문서삭제</Button> : ""}
                    {isStandby && isWriter ? <Button className="button" onClick={recallBtn}>문서회수</Button> : ""}
                </div>
                <div className={styles.buttonGroup}>
                    {isMyTurn && nextTurnNotSign && (
                        <Button variant="success" onClick={() => approvalBtn("승인")}>결재승인</Button>
                    )}
                    {!isMyTurn && nextTurnNotSign && !isDone && (
                        <Button variant="warning" onClick={cancelBtn}>결재취소</Button>
                    )}
                    {isMyTurn && !isWriter && nextTurnNotSign ?
                        <Button variant="danger" onClick={() => approvalBtn("반려")}>결재반려</Button> : ""
                    }
                    <Button className="button" onClick={() => navigate(-1)}>목록으로</Button>
                </div>
            </div>

            <div className={styles.divisionLine}></div>
            <div className={styles.lowerContainer}>
                <div className={styles.categoryTitle}>
                    <p>{documentData?.template?.category}</p>
                </div>

                <DocumentSignTable documentData={documentData} signLine={signLine}/>

                <div className={styles.editorContainer}>
                    <div className={styles.documentTitle}>
                        <p>제목 : </p>{documentData?.title}
                    </div>
                    <div>
                        <div>{isCompleted && parse(documentData?.content)}</div>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default DocumentDetail
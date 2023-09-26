import Dropdown from 'react-bootstrap/Dropdown';
import {Button} from "react-bootstrap";
import {useEffect, useState} from "react";
import fetcher from "../../../fetcher";
import {
    CATEGORY_LIST_API, DOCUMENT_READ_API, DOCUMENT_UPDATE_API, SHOW_CATEGORY_API,
} from "../../../constants/api_constans";
import ApprovalPathModal from "../../APPROVAL/ApprovalPathModal";
import useStore from "../../../store";
import {useNavigate, useParams} from "react-router-dom";
import {REPORT_DOCUMENT_COMPONENT} from "../../../constants/component_constants";
import {CKEditor} from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import Swal from "sweetalert2";
import {
    ButtonGroup, CategorySelect,
    CategoryTitle, DivisionLine, DocumentTitle, DropMenu,
    EditorContainer, LowerContainer, UpperContainer, Wrapper
} from "../../APPROVAL/Write";
import EditSignTable from "./EditSignTable";

function EditPage() {
    const {id} = useParams();
    const navigate = useNavigate();
    const {signLine} = useStore(state => state)
    const approvers = [signLine.signTurn1.id, signLine.signTurn2.id, signLine.signRefer ? signLine.signRefer.id : null];

    const [title, setTitle] = useState("")
    const [content, setContent] = useState("")
    const [category, setCategory] = useState("")
    const [categoryId, setCategoryId] = useState("")
    const [categoryList, setCategoryList] = useState([])
    const [showApprovalPathModal, setShowApprovalPathModal] = useState(false);
    const [formerSignLine, setFormerSignLine] = useState([])
    const [documentData, setDocumentData] = useState({});

    useEffect(() => {
        fetcher.get(CATEGORY_LIST_API)
            .then((res) => setCategoryList(res.data))
    }, [])

    useEffect(() => {
        fetcher.get(`${DOCUMENT_READ_API}/${id}`)
            .then((res) => {
                // 문서 정보
                const {document, groupedApprovals} = res.data
                setDocumentData(document)
                setCategory(document?.template?.category)
                setTitle(document?.title)
                setContent(document?.content)
                setFormerSignLine(groupedApprovals[document.dno])
            })
            .catch((err) => console.log(err))
    }, [id])

    const selectCategory = (id) => {
        fetcher.get(`${SHOW_CATEGORY_API}/${id}`)
            .then((res) => {
                setCategoryId(res.data?.id)
                setCategory(res.data?.category)
                setContent(res.data?.content)
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
        fetcher.put((`${DOCUMENT_UPDATE_API}/${id}`), {
            title: title,
            content: content,
            approvers: approvers,
            template: categoryId,
            status: 1,
        }).then(() => {
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
        <Wrapper>
            <UpperContainer>
                <CategorySelect>
                    <Dropdown>
                        <Dropdown.Toggle className="button">
                            {category ? category : "문서양식"}
                        </Dropdown.Toggle>
                        <DropMenu>
                            {categoryList?.map((data) => {
                                return (
                                    <Dropdown.Item key={data.id} onClick={() => selectCategory(data.id)}>
                                        {data.category}
                                    </Dropdown.Item>
                                )
                            })}
                        </DropMenu>
                    </Dropdown>
                    <Button className="button" onClick={() => setShowApprovalPathModal(true)}>결재라인</Button>
                </CategorySelect>

                <ButtonGroup>
                    <Button className="button" onClick={() => navigate(-1)}>작성취소</Button>
                    <Button className="button" onClick={handleSave}>상신하기</Button>
                </ButtonGroup>
            </UpperContainer>

            <DivisionLine/>

            <LowerContainer>
                <CategoryTitle>
                    <p>{category ? category : "양식을 선택하세요"}</p>
                </CategoryTitle>

                <EditSignTable documentData={documentData} signLine={signLine} formerSignLine={formerSignLine}/>

                <EditorContainer>
                    <DocumentTitle>
                        <p>제목 :</p>
                        <input value={title}
                               placeholder={"제목을 입력하세요"}
                               style={{margin: "10px 0"}}
                               onChange={(e) => setTitle(e.target.value)}
                        />
                    </DocumentTitle>

                  <CKEditor
                        editor={ClassicEditor}
                        data={content}
                        onChange={(event, editor)  => {
                            const data = editor.getData();
                            setContent(data)
                        }}
                    />
                </EditorContainer>
            </LowerContainer>

            <ApprovalPathModal showApprovalPathModal={showApprovalPathModal}
                               handleApprovalPathModalClose={() => setShowApprovalPathModal(false)}
            />
        </Wrapper>
    )
}

export default EditPage
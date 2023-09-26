import Dropdown from 'react-bootstrap/Dropdown';
import {Button} from "react-bootstrap";
import {useEffect, useState} from "react";
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
import styled from "styled-components";


function Write() {
    const navigate = useNavigate();
    const {signLine} = useStore(state => state)
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
                    <Button className="button" onClick={handleTempSave}>임시저장</Button>
                    <Button className="button" onClick={handleSave}>상신하기</Button>
                </ButtonGroup>
            </UpperContainer>

            <DivisionLine/>

            <LowerContainer>
                <CategoryTitle>
                    <p>{category ? category : "양식을 선택하세요"}</p>
                </CategoryTitle>

                <WriteSignTable/>

                <EditorContainer>
                    <DocumentTitle>
                        <p>제목 :</p>
                        <input value={title}
                               placeholder={"제목을 입력하세요"}
                               style={{margin: "10px 0"}}
                               onChange={(e) => setTitle(e.target.value)}/>
                    </DocumentTitle>

                    <CKEditor
                        editor={ClassicEditor}
                        data={content}
                        config={{placeholder: "양식을 입력 하세요."}}
                        onChange={(event, editor) => {
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

export default Write

export const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
  align-items: center;
`
export const UpperContainer = styled.div`
  width: 100%;
  height: 79px;
  display: flex;
  flex-direction: row;
  justify-content: space-around;
  padding: 20px;

  Button {
    margin: 0 10px
  }
`
export const CategorySelect = styled.div`
  display: flex;
  flex-direction: row;
`
export const DropMenu = styled(Dropdown.Menu)`
  --bs-dropdown-link-active-bg: rgba(125, 121, 242, 0.9);
`
export const ButtonGroup = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
`
export const DivisionLine = styled.div`
  border-bottom: 1px solid lightgray;
  width: 100%;
`
export const TitleDivisionLine = styled(DivisionLine)`
  margin-bottom: 10px;
`
export const LowerContainer = styled.div`
  max-width: 1200px;
  width: calc(100% - 30px);
  height: calc(100% - 80px);
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  margin: 10px;
  padding: 10px;
  border: 3px solid #e3e3e3;
`
export const CategoryTitle = styled.div`
  height: 70px;
  text-align: center;
  font-size: 22px;
  font-weight: bold;
  padding: 20px;
`
export const EditorContainer = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  margin: 10px 115px;
  justify-content: flex-start;
`
export const DocumentTitle = styled.div`
  display: flex;
  flex-direction: row;
  align-items: baseline;
  margin: 0 0 10px;

  p {
    font-size: 18px;
    font-weight: bold;
    margin-right: 20px;
  }

  input {
    width: calc(100% - 70px);
  }
`
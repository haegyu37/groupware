import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {
    DOCUMENT_DELETE_API, DOCUMENT_READ_API,
} from "../../constants/api_constans";
import {useNavigate, useParams} from "react-router-dom";
import {Button} from "react-bootstrap";
import TempDocumentSignTable from "./components/TempDocumentSignTable";
import Swal from "sweetalert2";
import parse from "html-react-parser";
import {
    ButtonGroup,
    CategorySelect,
    CategoryTitle,
    DivisionLine, DocumentTitle, EditorContainer,
    LowerContainer, TitleDivisionLine,
    UpperContainer,
    Wrapper
} from "../APPROVAL/Write";
import {EDIT_PAGE_COMPONENT} from "../../constants/component_constants";

function TempDocumentDetail() {
    const {id} = useParams();
    const navigate = useNavigate();
    const [documentData, setDocumentData] = useState({});
    const [signLine, setSignLine] = useState([]);
    const [isCompleted, setIsCompleted] = useState(false);

    useEffect(() => {
        fetcher.get(`${DOCUMENT_READ_API}/${id}`)
            .then((res) => {
                const {document, groupedApprovals} = res.data
                setDocumentData(document)
                // setSignLine(groupedApprovals[document.sno])
                setIsCompleted(true)
            })
            .catch((err) => console.log(err))
    }, [id])

    const routeEditPage = (id) => {
        navigate(`/page/${EDIT_PAGE_COMPONENT}/${id}`);
    }

    const deleteBtn = () => {
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
        <Wrapper>
            <UpperContainer>
                <CategorySelect>
                    <Button className="button" onClick={()=>routeEditPage(id)}>문서수정</Button>
                    <Button className="button" onClick={deleteBtn}>문서삭제</Button>
                </CategorySelect>
                <ButtonGroup>
                    <Button className="button" onClick={() => navigate(-1)}>목록으로</Button>
                </ButtonGroup>
            </UpperContainer>

            <DivisionLine/>

            <LowerContainer>
                <CategoryTitle>
                    <p>{documentData?.template?.category}</p>
                </CategoryTitle>

                <TempDocumentSignTable documentData={documentData} signLine={signLine}/>

                <EditorContainer>
                    <TitleDivisionLine/>
                    <DocumentTitle>
                        <p>제목 : </p>{documentData.title}
                    </DocumentTitle>
                    <TitleDivisionLine/>

                    <div>{isCompleted && parse(documentData.content)}</div>

                </EditorContainer>
            </LowerContainer>
        </Wrapper>
    )
}

export default TempDocumentDetail
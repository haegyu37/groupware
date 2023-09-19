import Sidebar from "../COMPONENT/LAYOUT/Sidebar/Sidebar";
import {Route, Routes} from "react-router-dom";
import Write from "./APPROVAL/Write";
import ReceiveDocument from "./DOCUMENT/ReceiveDocument";
import ReportDocument from "./DOCUMENT/ReportDocument";
import MyPage from "./MYPAGE/MyPage";
import AccountManagement from "./ACCOUNT-MNG/AccountManagement";
import DocumentRegistration from "./DOCUMENT-MNG/DocumentRegistration";
import AccountRegistration from "./ACCOUNT-MNG/AccountRegistration";
import Header from "../COMPONENT/LAYOUT/Header";
import {
    ACCOUNT_MANAGEMENT_COMPONENT,
    ACCOUNT_REGISTRATION_COMPONENT,
    ALL_DOCUMENT_LIST_COMPONENT,
    DOCUMENT_DETAIL_COMPONENT,
    DOCUMENT_REGISTRATION_COMPONENT,
    DOCUMENT_WRITE_COMPONENT, EDIT_PAGE_COMPONENT,
    MY_PAGE_COMPONENT,
    RECEIVE_DOCUMENT_COMPONENT,
    REPORT_DOCUMENT_COMPONENT,
    TEMP_DOCUMENT_COMPONENT
} from "../constants/component_constants";
import TempDocument from "./DOCUMENT/TempDocument";
import DocumentDetail from "./DOCUMENT/DocumentDetail";
import TempDocumentDetail from "./DOCUMENT/TempDocumentDetail";
import AllDocument from "./DOCUMENT-MNG/AllDocument";
import styled from "styled-components";
import {styleConstants} from "../STYLES/styleConstants";
import EditPage from "./DOCUMENT/components/EditPage";

function Page() {
    return (
        <>
            <Header/>
            <Wrapper>
                <Sidebar/>
                <Container>
                    <Routes>
                        <Route path={DOCUMENT_WRITE_COMPONENT} element={<Write/>}/>
                        <Route path={RECEIVE_DOCUMENT_COMPONENT} element={<ReceiveDocument/>}/>
                        <Route path={REPORT_DOCUMENT_COMPONENT} element={<ReportDocument/>}/>
                        <Route path={MY_PAGE_COMPONENT} element={<MyPage/>}/>
                        <Route path={TEMP_DOCUMENT_COMPONENT} element={<TempDocument/>}/>
                        <Route path={`${DOCUMENT_DETAIL_COMPONENT}/:id`} element={<DocumentDetail/>}/>
                        <Route path={`${TEMP_DOCUMENT_COMPONENT}/:id`} element={<TempDocumentDetail/>}/>
                        <Route path={`${EDIT_PAGE_COMPONENT}/:id`} element={<EditPage/>}/>
                        {/* 관리자 전용 PAGE ↓ */}
                        <Route path={DOCUMENT_REGISTRATION_COMPONENT} element={<DocumentRegistration/>}/>
                        <Route path={ALL_DOCUMENT_LIST_COMPONENT} element={<AllDocument/>}/>
                        <Route path={ACCOUNT_MANAGEMENT_COMPONENT} element={<AccountManagement/>}/>
                        <Route path={ACCOUNT_REGISTRATION_COMPONENT} element={<AccountRegistration/>}/>
                    </Routes>
                </Container>
            </Wrapper>
        </>
    )
}

export default Page

const Wrapper = styled.div`
  display: flex;
  flex-direction: row;
  width: 100%;
  height: calc(100% - ${styleConstants.layout.header.height});
`
const Container = styled.div`
  display: flex;
  flex-direction: row;
  width: calc(100% - ${styleConstants.layout.sidebar.width});
  height: 100%;
`
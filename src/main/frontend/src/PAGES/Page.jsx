import Sidebar from "../COMPONENT/LAYOUT/Sidebar/Sidebar";
import {Route, Routes, useNavigate} from "react-router-dom";
import Write from "./APPROVAL/Write";
import ReceiveDocument from "./DOCUMENT/ReceiveDocument";
import ReportDocument from "./DOCUMENT/ReportDocument";
import styles from "./Page.module.css"
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
    DOCUMENT_WRITE_COMPONENT,
    FORBIDDEN_COMPONENT,
    MY_PAGE_COMPONENT,
    RECEIVE_DOCUMENT_COMPONENT,
    REPORT_DOCUMENT_COMPONENT,
    TEMP_DOCUMENT_COMPONENT
} from "../constants/component_constants";
import TempDocument from "./DOCUMENT/TempDocument";
import DocumentDetail from "./DOCUMENT/DocumentDetail";
import TempDocumentDetail from "./DOCUMENT/TempDocumentDetail";
import useStore from "../store";
import AllDocument from "./DOCUMENT-MNG/AllDocument";

function Page() {
    const {myAccount} = useStore(state => state);

    const isAdmin = myAccount?.authority === "ADMIN";

    return (
        <>
            <Header/>
            <div className={styles.wrapper}>
                <Sidebar/>
                <div className={styles.container}>
                    <Routes>
                        <Route path={DOCUMENT_WRITE_COMPONENT} element={<Write/>}/>
                        <Route path={RECEIVE_DOCUMENT_COMPONENT} element={<ReceiveDocument/>}/>
                        <Route path={REPORT_DOCUMENT_COMPONENT} element={<ReportDocument/>}/>
                        <Route path={TEMP_DOCUMENT_COMPONENT} element={<TempDocument/>}/>
                        <Route path={`${DOCUMENT_DETAIL_COMPONENT}/:id`} element={<DocumentDetail/>}/>
                        <Route path={`${TEMP_DOCUMENT_COMPONENT}/:id`} element={<TempDocumentDetail/>}/>
                        <Route path={MY_PAGE_COMPONENT} element={<MyPage/>}/>

                        {/* //TODO: 관리자 권한이 없는 계정이 관리자페이지 url 접근 -> ForbiddenPage 이동 */}
                        {isAdmin &&
                            <>
                                <Route path={DOCUMENT_REGISTRATION_COMPONENT} element={<DocumentRegistration/>}/>
                                <Route path={ALL_DOCUMENT_LIST_COMPONENT} element={<AllDocument/>}/>
                                <Route path={ACCOUNT_MANAGEMENT_COMPONENT} element={<AccountManagement/>}/>
                                <Route path={ACCOUNT_REGISTRATION_COMPONENT} element={<AccountRegistration/>}/>
                            </>
                        }
                    </Routes>
                </div>
            </div>
        </>
    )
}

export default Page
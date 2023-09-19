import {Link} from "react-router-dom";
import {
    RECEIVE_DOCUMENT_COMPONENT,
    REPORT_DOCUMENT_COMPONENT,
    TEMP_DOCUMENT_COMPONENT
} from "../../../constants/component_constants";
import {DivisionLine, MainLink, MenuCard} from "./DocumentManagementMenu";

function DocumentMenu({menuName}) {
    return (
        <MenuCard>
            <img src={require("../../../IMAGES/folder.png")}/>

            <DivisionLine/>

            <MainLink>
                <Link to={`/page/${RECEIVE_DOCUMENT_COMPONENT}`} onClick={menuName}>수신문서</Link>
                <Link to={`/page/${REPORT_DOCUMENT_COMPONENT}`} onClick={menuName}>상신문서</Link>
                <Link to={`/page/${TEMP_DOCUMENT_COMPONENT}`} onClick={menuName}>임시보관함</Link>
            </MainLink>
        </MenuCard>
    )
}

export default DocumentMenu
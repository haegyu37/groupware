import {Link} from "react-router-dom";
import {DOCUMENT_WRITE_COMPONENT} from "../../../constants/component_constants";
import {DivisionLine, MainLink, MenuCard} from "./DocumentManagementMenu";

function ApprovalMenu({menuName}) {
    return(
        <>
            <MenuCard>
                <img src={require("../../../IMAGES/checklist.png")}/>

                <DivisionLine/>

                <MainLink>
                    <Link to={`/page/${DOCUMENT_WRITE_COMPONENT}`} onClick={menuName}>작성하기</Link>
                </MainLink>
            </MenuCard>
        </>
    )
}

export default ApprovalMenu
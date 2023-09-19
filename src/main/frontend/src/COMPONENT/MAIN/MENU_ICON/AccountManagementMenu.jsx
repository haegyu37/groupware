import {Link} from "react-router-dom";
import {ACCOUNT_MANAGEMENT_COMPONENT, ACCOUNT_REGISTRATION_COMPONENT} from "../../../constants/component_constants";
import {DivisionLine, MainLink, MenuCard} from "./DocumentManagementMenu";

function AccountManagementMenu({menuName, isAdmin}) {
    return (
        <MenuCard isAdmin={isAdmin}>
            <img src={require("../../../IMAGES/management.png")}/>

            <DivisionLine/>

            <MainLink isAdmin={isAdmin}>
                <Link to={`/page/${ACCOUNT_REGISTRATION_COMPONENT}`} onClick={menuName}>계정등록</Link>
                <Link to={`/page/${ACCOUNT_MANAGEMENT_COMPONENT}`} onClick={menuName}>계정관리</Link>
            </MainLink>
        </MenuCard>
    )
}

export default AccountManagementMenu




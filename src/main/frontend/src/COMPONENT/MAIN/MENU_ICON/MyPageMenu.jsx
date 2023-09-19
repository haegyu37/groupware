import {Link} from "react-router-dom";
import {MY_PAGE_COMPONENT} from "../../../constants/component_constants";
import {DivisionLine, MainLink, MenuCard} from "./DocumentManagementMenu";

function MyPageMenu({menuName}) {
    return (
        <MenuCard>
            <img src={require("../../../IMAGES/profile.png")}/>

            <DivisionLine/>

            <MainLink>
                <Link to={`/page/${MY_PAGE_COMPONENT}`} onClick={menuName}>내정보관리</Link>
            </MainLink>
        </MenuCard>
    )
}

export default MyPageMenu
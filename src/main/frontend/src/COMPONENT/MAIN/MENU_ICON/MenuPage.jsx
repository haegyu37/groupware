import ApprovalMenu from "./ApprovalMenu";
import DocumentMenu from "./DocumentMenu";
import MyPageMenu from "./MyPageMenu";
import DocumentManagementMenu from "./DocumentManagementMenu";
import AccountManagementMenu from "./AccountManagementMenu";
import useStore from "../../../store";
import styled from "styled-components";
function MenuPage() {

    const {changeTitle, myAccount} = useStore(state => state)
    const isAdmin = myAccount?.authority === "ADMIN";

    const menuName = (e) =>{
        const selectMenu = e.target.textContent;
        changeTitle(selectMenu);
    }

    return(
        <Container>
            <ApprovalMenu menuName={menuName}/>
            <DocumentMenu menuName={menuName}/>
            <MyPageMenu menuName={menuName}/>

            {isAdmin &&
                <>
                    <DocumentManagementMenu menuName={menuName} isAdmin={isAdmin}/>
                    <AccountManagementMenu menuName={menuName} isAdmin={isAdmin}/>
                </>
            }
        </Container>
    )
}

export default MenuPage

const Container = styled.div`
  display: flex;
  flex-direction: row;
`
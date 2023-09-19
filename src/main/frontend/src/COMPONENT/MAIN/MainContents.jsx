import MenuPage from "./MENU_ICON/MenuPage";
import Profile from "./components/Profile";
import ApprovalNotice from "./components/ApprovalNotice";
import Header from "../LAYOUT/Header";
import Calender from "./components/Calender";
import {useCookies} from "react-cookie";
import {ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE} from "../../constants/constants";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {LOGIN_COMPONENT} from "../../constants/component_constants";
import styled from "styled-components";
import {styleConstants} from "../../STYLES/styleConstants";

function MainContents() {

    const [accessCookie, ,] = useCookies([ACCESS_TOKEN_COOKIE]);
    const [refreshCookie, ,] = useCookies([REFRESH_TOKEN_COOKIE]);
    const navigate = useNavigate();

    useEffect(() => {
        if (!(accessCookie && refreshCookie)) {
            navigate(LOGIN_COMPONENT)
        }
    }, [])

    return (
        <Wrapper>
            <Header/>
            <Contents>
                <ContentsLine>
                    <Profile/>
                    <ApprovalNotice/>
                    {/*<Notice/>*/}
                </ContentsLine>
                <ContentsLine>
                    <MenuPage/>
                    <Calender/>
                </ContentsLine>
            </Contents>
        </Wrapper>
    )
}

export default MainContents

const Wrapper = styled.div`
  height: 100%;
  width: 100%;
`
const Contents = styled.div`
  height: calc(100% - ${styleConstants.layout.sidebar.width});
  width: 100%;
  display: flex;
  flex-direction: column;
`
const ContentsLine = styled.div`
  display: flex;
  flex-direction: row;
`
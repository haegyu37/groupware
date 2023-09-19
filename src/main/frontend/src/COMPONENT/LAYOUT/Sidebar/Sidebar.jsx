import Accordion from 'react-bootstrap/Accordion';
import {Link} from "react-router-dom";
import {useState} from "react";
import useStore from "../../../store";
import SidebarProfile from "./SidebarProfile";
import {
    ACCOUNT_MANAGEMENT_COMPONENT,
    ACCOUNT_REGISTRATION_COMPONENT,
    ALL_DOCUMENT_LIST_COMPONENT,
    DOCUMENT_REGISTRATION_COMPONENT,
    DOCUMENT_WRITE_COMPONENT,
    MY_PAGE_COMPONENT,
    RECEIVE_DOCUMENT_COMPONENT,
    REPORT_DOCUMENT_COMPONENT,
    TEMP_DOCUMENT_COMPONENT
} from "../../../constants/component_constants";
import styled, {css} from "styled-components";
import {styleConstants} from "../../../STYLES/styleConstants";

function Sidebar() {
    const [activeLink, setActiveLink] = useState("");

    const {changeTitle, myAccount} = useStore(state => state)

    const isAdmin = myAccount?.authority === "ADMIN";

    const menuName = (e) => {
        const selectMenu = e.target.textContent;
        changeTitle(selectMenu);
    }

    const handleLink = (link) => {
        setActiveLink(link);
    }

    return (
        <Wrapper>
            <SidebarProfile/>
            <MenuContainer>
                <Accordion eventKey="0" flush>
                    <AccordionItem eventKey="0">
                        <Accordion.Header>
                            <MenuImg src={require("../../../IMAGES/checklist.png")}/>
                            <MenuTitle>전자결재</MenuTitle>
                        </Accordion.Header>
                        <Accordion.Body onClick={menuName}>
                            <UnderLine to={`/page/${DOCUMENT_WRITE_COMPONENT}`}
                                       className={activeLink === "write" ? "active" : ""}
                                       onClick={() => handleLink(DOCUMENT_WRITE_COMPONENT)}
                            >작성하기</UnderLine>
                        </Accordion.Body>
                    </AccordionItem>
                    <AccordionItem eventKey="1">
                        <Accordion.Header>
                            <MenuImg src={require("../../../IMAGES/folder.png")}/>
                            <MenuTitle>문서함</MenuTitle>
                        </Accordion.Header>
                        <Accordion.Body onClick={menuName}>
                            <UnderLine to={`/page/${RECEIVE_DOCUMENT_COMPONENT}`}
                                       className={activeLink === RECEIVE_DOCUMENT_COMPONENT ? "active" : ""}
                                       onClick={() => handleLink(RECEIVE_DOCUMENT_COMPONENT)}
                            >수신문서</UnderLine>
                        </Accordion.Body>
                        <Accordion.Body onClick={menuName}>
                            <UnderLine to={`/page/${REPORT_DOCUMENT_COMPONENT}`}
                                       className={activeLink === REPORT_DOCUMENT_COMPONENT ? "active" : ""}
                                       onClick={() => handleLink(REPORT_DOCUMENT_COMPONENT)}
                            >상신문서</UnderLine>
                        </Accordion.Body>
                        <Accordion.Body onClick={menuName}>
                            <UnderLine to={`/page/${TEMP_DOCUMENT_COMPONENT}`}
                                       className={activeLink === TEMP_DOCUMENT_COMPONENT ? "active" : ""}
                                       onClick={() => handleLink(TEMP_DOCUMENT_COMPONENT)}
                            >임시보관함</UnderLine>
                        </Accordion.Body>
                    </AccordionItem>

                    <AccordionItem eventKey="2">
                        <Accordion.Header>
                            <MenuImg src={require("../../../IMAGES/profile.png")}/>
                            <MenuTitle>마이페이지</MenuTitle>
                        </Accordion.Header>
                        <Accordion.Body onClick={menuName}>
                            <UnderLine to={`/page/${MY_PAGE_COMPONENT}`}
                                       className={activeLink === MY_PAGE_COMPONENT ? "active" : ""}
                                       onClick={() => handleLink(MY_PAGE_COMPONENT)}
                            >내정보관리</UnderLine>
                        </Accordion.Body>
                    </AccordionItem>

                    {isAdmin &&
                        <>
                            <AccordionItem eventKey="3" isAdmin={isAdmin}>
                                <Accordion.Header>
                                    <MenuImg src={require("../../../IMAGES/process.png")}/>
                                    <MenuTitle isAdmin={isAdmin}>문서관리</MenuTitle>
                                </Accordion.Header>
                                <Accordion.Body onClick={menuName}>
                                    <UnderLine to={`/page/${DOCUMENT_REGISTRATION_COMPONENT}`}  isAdmin={isAdmin}
                                               className={activeLink === DOCUMENT_REGISTRATION_COMPONENT ? "active" : ""}
                                               onClick={() => handleLink(DOCUMENT_REGISTRATION_COMPONENT)}
                                    >양식관리</UnderLine>
                                </Accordion.Body>
                                <Accordion.Body onClick={menuName}>
                                    <UnderLine to={`/page/${ALL_DOCUMENT_LIST_COMPONENT}`}  isAdmin={isAdmin}
                                               className={activeLink === ALL_DOCUMENT_LIST_COMPONENT ? "active" : ""}
                                               onClick={() => handleLink(ALL_DOCUMENT_LIST_COMPONENT)}
                                    >결재문서</UnderLine>
                                </Accordion.Body>
                            </AccordionItem>

                            <AccordionItem eventKey="4" isAdmin={isAdmin}>
                                <Accordion.Header>
                                    <MenuImg src={require("../../../IMAGES/management.png")}/>
                                    <MenuTitle isAdmin={isAdmin}>계정관리</MenuTitle>
                                </Accordion.Header>
                                <Accordion.Body onClick={menuName}>
                                    <UnderLine to={`/page/${ACCOUNT_REGISTRATION_COMPONENT}`} isAdmin={isAdmin}
                                               className={activeLink === ACCOUNT_REGISTRATION_COMPONENT ? "active" : ""}
                                               onClick={() => handleLink(ACCOUNT_REGISTRATION_COMPONENT)}
                                    >계정등록</UnderLine>
                                </Accordion.Body>
                                <Accordion.Body onClick={menuName}>
                                    <UnderLine to={`/page/${ACCOUNT_MANAGEMENT_COMPONENT}`}  isAdmin={isAdmin}
                                               className={activeLink === ACCOUNT_MANAGEMENT_COMPONENT ? "active" : ""}
                                               onClick={() => handleLink(ACCOUNT_MANAGEMENT_COMPONENT)}
                                    >계정관리</UnderLine>
                                </Accordion.Body>
                            </AccordionItem>
                        </>
                    }
                </Accordion>
            </MenuContainer>
        </Wrapper>
    )
}

export default Sidebar

const Wrapper = styled.div`
  width: ${styleConstants.layout.sidebar.width};
  height: 100%;
  border-right: 1px solid #afb0b1;
`
const MenuContainer = styled.div`
  height: 60%;
  display: flex;
  flex-direction: column;
`
const MenuImg = styled.img`
  width: 40px;
  height: 40px;
`
const MenuTitle = styled.p`
  font-weight: bold;
  padding-left: 20px;
  color: ${props => props.isAdmin ? "#fa3e0c" : "#4429f2"};
`
const AccordionItem = styled(Accordion.Item)`
  --bs-accordion-border-color: none;
  --bs-accordion-btn-bg: none;
  --bs-accordion-btn-focus-box-shadow: none;
  --bs-accordion-btn-padding-x: 4rem;
  --bs-accordion-body-padding-x: 4rem;
  --bs-accordion-btn-padding-y: 0.7rem;
  --bs-accordion-body-padding-y: 0.5rem;
  --bs-accordion-active-bg: ${props => props.isAdmin ? "rgba(250, 62, 12, 0.21)" : "rgba(125, 121, 242, 0.37)"};
`
const UnderLine = styled(Link)`
  font-size: 18px;
  text-decoration: none;
  line-height: 1.4;
  background-repeat: no-repeat;
  background-size: 0 100%;
  transition: background-size 0.3s;
  cursor: pointer;
  margin-left: 20px;
  color: #4429f2;
  background-image: linear-gradient(transparent 50%, rgba(125, 121, 242, 0.37));
  &:hover {
    font-weight: 700;
    background-size: 100% 100%;
  }
  &.active {
    font-weight: 700;
    background-size: 100% 100%;
  }
  ${props => props.isAdmin && css`
    color: #fa3e0c;
    background-image: linear-gradient(transparent 50%, rgba(250, 62, 12, 0.21));
  `}
`


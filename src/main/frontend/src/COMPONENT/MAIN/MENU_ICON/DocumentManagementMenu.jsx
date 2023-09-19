import {Link} from "react-router-dom";
import {ALL_DOCUMENT_LIST_COMPONENT, DOCUMENT_REGISTRATION_COMPONENT} from "../../../constants/component_constants";
import styled from "styled-components";

function DocumentManagementMenu({menuName, isAdmin}) {
    return(
            <MenuCard isAdmin={isAdmin}>
                <img src={require("../../../IMAGES/process.png")}/>

                <DivisionLine/>

                <MainLink isAdmin={isAdmin}>
                    <Link to={`/page/${DOCUMENT_REGISTRATION_COMPONENT}`} onClick={menuName}>양식관리</Link>
                    <Link to={`/page/${ALL_DOCUMENT_LIST_COMPONENT}`} onClick={menuName}>결재문서</Link>
                </MainLink>
            </MenuCard>
    )
}

export default DocumentManagementMenu

export const MenuCard = styled.div`
  padding: 20px;
  margin: 20px 10px;
  display: flex;
  flex-direction: column;
  border: ${props => props.isAdmin ? "1px solid rgba(250, 62, 12, 0.6)" : "solid 1px rgba(68, 41, 242, 0.6)"};
  width: 165px;
  height: 280px;
  border-radius: 20px;
  img {
    width: 80px;
    height: 80px;
    padding: 15px 15px;
    margin-left: 25px;
    display: flex;
    flex-direction: row;
    justify-content: center;
    text-align: center;
  }
  &:first-child {
    margin-left: 30px;
  }
   
`
export const DivisionLine = styled.div`
  border-top: 1px solid #9b9b9d;
  margin: 20px auto;
  width: 100px;
`
export const MainLink = styled.div`
  display: flex;
  flex-direction: column;
  text-align: center;
  font-size: 18px;
  font-weight: bold;
  a {
    text-decoration: none;
    padding-bottom: 10px;
    color: ${props=>props.isAdmin ? "#FA3E0C" : "#4429f2"};
  }
`
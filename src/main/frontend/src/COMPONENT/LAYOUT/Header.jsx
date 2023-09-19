import {Link} from "react-router-dom";
import useStore from "../../store";
import {MAIN_COMPONENT} from "../../constants/component_constants";
import styled from "styled-components";
import {styleConstants} from "../../STYLES/styleConstants";

function Header() {
    const {headerTitle} = useStore(state => state)
    const {changeTitle} = useStore(state => state)

    return (
        <HeaderWrapper>
            <Title onClick={()=>changeTitle("메인")}>
                <Link to={MAIN_COMPONENT}><p>원 인터내셔널</p></Link>
            </Title>
            <HeaderLine>
                <p>{headerTitle}</p>
            </HeaderLine>
        </HeaderWrapper>
    );
}
export default Header;

const HeaderWrapper = styled.div`
    height: ${styleConstants.layout.header.height}
`

const Title = styled.div`
  padding: 5px 20px;
  font-size: 30px;
  font-weight: bold;
  p {
    color: #4429f2; 
  }
`

const HeaderLine = styled.div`
  background-color: #4429f2;
  color: white;
  font-weight: bold;
  padding: 8px 40px;
`

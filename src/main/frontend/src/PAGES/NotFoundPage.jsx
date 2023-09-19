import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {MAIN_COMPONENT} from "../constants/component_constants";
import styled from "styled-components";


function NotFoundPage() {
    return(
        <Wrapper>
            <img src={require("../IMAGES/404.jpg")}/>
            <h3>잘못된 접근 입니다.</h3>
            <Link to={MAIN_COMPONENT}>
                <Button variant="primary">메인으로</Button>
            </Link>
        </Wrapper>
    )
}
export default NotFoundPage

export const Wrapper = styled.div`
  display:flex;
  flex-direction:column;
  align-items:center;
  img{
    width: 500px;
    margin-top: 200px;
  }
  h3{
    padding: 30px;
  }
`
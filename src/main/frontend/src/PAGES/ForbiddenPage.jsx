import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {MAIN_COMPONENT} from "../constants/component_constants";
import {Wrapper} from "./NotFoundPage";


function NotFound() {
    return(
        <Wrapper>
            <img src={require("../IMAGES/403.png")}/>
            <h3>접근이 제한된 페이지입니다.</h3>
            <Link to={MAIN_COMPONENT}>
                <Button variant="warning">메인으로</Button>
            </Link>
        </Wrapper>
    )
}

export default NotFound
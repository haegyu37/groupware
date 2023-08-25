import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {MAIN_COMPONENT} from "../constants/component_constants";


function NotFound() {
    return(
        <div style={{
            display:"flex",
            flexDirection:"column",
            alignItems:"center"
        }}>
            <img src={require("../IMAGES/403.png")} style={{width:"500px", marginTop:"200px"}}/>
            <h3 style={{padding:"30px"}}>접근이 제한된 페이지입니다.</h3>
            <Link to={MAIN_COMPONENT}>
                <Button variant="warning">메인으로</Button>
            </Link>
        </div>
    )
}

export default NotFound
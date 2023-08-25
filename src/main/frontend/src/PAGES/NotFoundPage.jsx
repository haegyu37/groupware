import {Button} from "react-bootstrap";
import {Link} from "react-router-dom";
import {MAIN_COMPONENT} from "../constants/component_constants";


function NotFoundPage() {
    return(
        <div style={{
            display:"flex",
            flexDirection:"column",
            alignItems:"center"
        }}>
            <img src={require("../IMAGES/404.jpg")} style={{width:"500px", marginTop:"200px"}}/>
            <h3 style={{padding:"30px"}}>잘못된 접근 입니다.</h3>
            <Link to={MAIN_COMPONENT}>
                <Button variant="primary">메인으로</Button>
            </Link>
        </div>
    )
}

export default NotFoundPage
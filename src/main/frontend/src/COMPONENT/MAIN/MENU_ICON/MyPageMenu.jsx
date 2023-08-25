import {Link} from "react-router-dom";
import styles from "./Menu.module.css";
import {MY_PAGE_COMPONENT} from "../../../constants/component_constants";

function MyPageMenu({menuName}) {
    return(
        <>
            <div className={styles.mainMenu}>
                <img className={styles.icon} src={require("../../../IMAGES/profile.png")}/>

                <div className={styles.divisionLine}/>

                <div className={styles.mainLink}>
                    <Link to={`/page/${MY_PAGE_COMPONENT}`} onClick={menuName}>내정보관리</Link>
                </div>
            </div>
        </>
    )
}

export default MyPageMenu
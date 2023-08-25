import {Link} from "react-router-dom";
import styles from "./Menu.module.css"
import {DOCUMENT_WRITE_COMPONENT} from "../../../constants/component_constants";

function ApprovalMenu({menuName}) {
    return(
        <>
            <div className={styles.mainMenu}>
                <img className={styles.icon} src={require("../../../IMAGES/checklist.png")}/>

                <div className={styles.divisionLine}/>

                <div className={styles.mainLink}>
                    <Link to={`/page/${DOCUMENT_WRITE_COMPONENT}`} onClick={menuName}>작성하기</Link>
                </div>
            </div>
        </>
    )
}

export default ApprovalMenu
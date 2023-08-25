import {Link} from "react-router-dom";
import styles from "./Menu.module.css";
import {
    RECEIVE_DOCUMENT_COMPONENT,
    REPORT_DOCUMENT_COMPONENT,
    TEMP_DOCUMENT_COMPONENT
} from "../../../constants/component_constants";

function DocumentMenu({menuName}) {
    return(
        <>
            <div className={styles.mainMenu}>
                <img className={styles.icon} src={require("../../../IMAGES/folder.png")}/>

                <div className={styles.divisionLine}/>

                <div className={styles.mainLink}>
                    <Link to={`/page/${RECEIVE_DOCUMENT_COMPONENT}`} onClick={menuName}>수신문서</Link>
                    <Link to={`/page/${REPORT_DOCUMENT_COMPONENT}`} onClick={menuName}>상신문서</Link>
                    <Link to={`/page/${TEMP_DOCUMENT_COMPONENT}`} onClick={menuName}>임시보관함</Link>
                </div>
            </div>
        </>
    )
}

export default DocumentMenu
import {Link} from "react-router-dom";
import styles from "./Menu.module.css"
import {ALL_DOCUMENT_LIST_COMPONENT, DOCUMENT_REGISTRATION_COMPONENT} from "../../../constants/component_constants";

function DocumentManagementMenu({menuName}) {
    return(
        <>
            <div className={styles.mainMenu}
                style={{border: "1px solid rgba(250, 62, 12, 0.6)"}}
            >
                <img className={styles.icon} src={require("../../../IMAGES/process.png")}/>

                <div className={styles.divisionLine}/>

                <div className={styles.mainLink}>
                    <Link to={`/page/${DOCUMENT_REGISTRATION_COMPONENT}`}
                          style={{color: "#FA3E0C"}}
                          onClick={menuName}
                    >양식관리</Link>
                    <Link to={`/page/${ALL_DOCUMENT_LIST_COMPONENT}`}
                          style={{color: "#FA3E0C"}}
                          onClick={menuName}
                    >결재문서</Link>
                </div>
            </div>
        </>
    )
}

export default DocumentManagementMenu
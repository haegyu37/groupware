import {Link} from "react-router-dom";
import styles from "./Menu.module.css";
import {ACCOUNT_MANAGEMENT_COMPONENT, ACCOUNT_REGISTRATION_COMPONENT} from "../../../constants/component_constants";

function AccountManagementMenu({menuName}) {
    return(
        <>
            <div className={styles.mainMenu}
                 style={{border: "1px solid rgba(250, 62, 12, 0.6)"}}
            >
                <img className={styles.icon} src={require("../../../IMAGES/management.png")}/>

                <div className={styles.divisionLine}/>

                <div className={styles.mainLink}>
                    <Link to={`/page/${ACCOUNT_REGISTRATION_COMPONENT}`}
                          style={{color: "#FA3E0C"}}
                          onClick={menuName}
                    >계정등록</Link>
                    <Link to={`/page/${ACCOUNT_MANAGEMENT_COMPONENT}`}
                          style={{color: "#FA3E0C"}}
                          onClick={menuName}
                    >계정관리</Link>
                </div>
            </div>
        </>
    )
}

export default AccountManagementMenu
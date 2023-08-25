import styles from "./Header.module.css"
import {Link} from "react-router-dom";
import useStore from "../../store";
import {MAIN_COMPONENT} from "../../constants/component_constants";


function Header() {
    const {headerTitle} = useStore(state => state)
    const {changeTitle} = useStore(state => state)

    return (
        <div className={styles.header}>
            <div className={styles.headerTitle}  onClick={()=>changeTitle("메인")}>
                <Link to={MAIN_COMPONENT}>원 인터내셔널</Link>
            </div>
            <div className={styles.headerLine}>
                <p>{headerTitle}</p>
            </div>
        </div>
    );
}

export default Header;
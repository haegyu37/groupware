import styles from "../../Sidebar.module.css";
import {Link} from "react-router-dom";
import Accordion from "react-bootstrap/Accordion";

const Body = ({menuName, activeLink ,handleLink, componentLink, buttonTitle})=>{
    return(
            <Accordion.Body className={styles.accordionBody} onClick={menuName}>
                <Link to={componentLink}
                      className={`${styles.underline} ${styles.blue} ${activeLink === componentLink ? styles.active : ""}`}
                      onClick={() => handleLink(componentLink)}
                >{buttonTitle}</Link>
            </Accordion.Body>
    )
}
export default Body
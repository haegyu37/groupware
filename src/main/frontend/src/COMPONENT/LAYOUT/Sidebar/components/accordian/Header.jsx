import styles from "../../Sidebar.module.css";
import Accordion from "react-bootstrap/Accordion";

const Header = ({imgLink, headerName})=>{
    return(
        <Accordion.Header className={styles.menu}>
            <img src={imgLink}/>
            <p>{headerName}</p>
        </Accordion.Header>
    )
}
export default Header
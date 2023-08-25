import styles from "../../Sidebar.module.css";
import Accordion from "react-bootstrap/Accordion";
import {Link} from "react-router-dom";
import Body from "./Body";
import Header from "./Header";

const Item = ({handleLink, activeLink,headerName, imgLink, eventKey }, {componentLink, buttonTitle, menuName})=>{
    return(
        <Accordion.Item eventKey={eventKey} className={styles.accordion}>
            <Header headerName={headerName} imgLink={imgLink}/>
            <Body activeLink={activeLink}
                  handleLink={handleLink}

                  componentLink={componentLink}
                  buttonTitle={buttonTitle}
                  menuName={menuName}/>
        </Accordion.Item>
    )
}

export default Item
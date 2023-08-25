import styles from "./Sidebar.module.css"
import Accordion from 'react-bootstrap/Accordion';
import {Link} from "react-router-dom";
import {useState} from "react";
import useStore from "../../../store";
import SidebarProfile from "./components/SidebarProfile";
import {
    ACCOUNT_MANAGEMENT_COMPONENT,
    ACCOUNT_REGISTRATION_COMPONENT,
    ALL_DOCUMENT_LIST_COMPONENT,
    DOCUMENT_REGISTRATION_COMPONENT,
    DOCUMENT_WRITE_COMPONENT,
    MY_PAGE_COMPONENT,
    RECEIVE_DOCUMENT_COMPONENT,
    REPORT_DOCUMENT_COMPONENT,
    TEMP_DOCUMENT_COMPONENT
} from "../../../constants/component_constants";

function Sidebar() {
    const [activeLink, setActiveLink] = useState("");

    const {changeTitle, myAccount} = useStore(state => state)

    const isAdmin = myAccount?.authority === "ADMIN";

    const menuName = (e) => {
        const selectMenu = e.target.textContent;
        changeTitle(selectMenu);
    }

    const handleLink = (link) => {
        setActiveLink(link);
    }

    return (
        <div className={styles.wrapper}>
            <SidebarProfile/>
            <div className={styles.menuContainer}>
                <Accordion eventKey="0" flush>
                    <Accordion.Item eventKey="0" className={styles.accordion}>
                        <Accordion.Header className={styles.menu}>
                            <img src={require("../../../IMAGES/checklist.png")}/>
                            <p>전자결재</p>
                        </Accordion.Header>
                        <Accordion.Body onClick={menuName}>
                            <Link to={`/page/${DOCUMENT_WRITE_COMPONENT}`}
                                  className={`${styles.underline} ${styles.blue} ${activeLink === "write" ? styles.active : ""}`}
                                  onClick={() => handleLink("write")}
                            >작성하기</Link>
                        </Accordion.Body>
                    </Accordion.Item>
                    <Accordion.Item eventKey="1" className={styles.accordion}>
                        <Accordion.Header className={styles.menu}>
                            <img src={require("../../../IMAGES/folder.png")}/>
                            <p>문서함</p>
                        </Accordion.Header>
                        <Accordion.Body onClick={menuName}>
                            <Link to={`/page/${RECEIVE_DOCUMENT_COMPONENT}`}
                                  className={`${styles.underline} ${styles.blue} ${activeLink === RECEIVE_DOCUMENT_COMPONENT ? styles.active : ""}`}
                                  onClick={() => handleLink(REPORT_DOCUMENT_COMPONENT)}
                            >수신문서</Link>
                        </Accordion.Body>
                        <Accordion.Body onClick={menuName}>
                            <Link to={`/page/${REPORT_DOCUMENT_COMPONENT}`}
                                  className={`${styles.underline} ${styles.blue} ${activeLink === REPORT_DOCUMENT_COMPONENT ? styles.active : ""}`}
                                  onClick={() => handleLink(REPORT_DOCUMENT_COMPONENT)}
                            >상신문서</Link>
                        </Accordion.Body>
                        <Accordion.Body onClick={menuName}>
                            <Link to={`/page/${TEMP_DOCUMENT_COMPONENT}`}
                                  className={`${styles.underline} ${styles.blue} ${activeLink === TEMP_DOCUMENT_COMPONENT ? styles.active : ""}`}
                                  onClick={() => handleLink(TEMP_DOCUMENT_COMPONENT)}
                            >임시보관함</Link>
                        </Accordion.Body>
                    </Accordion.Item>

                    <Accordion.Item eventKey="2" className={styles.accordion}>
                        <Accordion.Header className={styles.menu}>
                            <img src={require("../../../IMAGES/profile.png")}/>
                            <p>마이페이지</p>
                        </Accordion.Header>
                        <Accordion.Body onClick={menuName}>
                            <Link to={`/page/${MY_PAGE_COMPONENT}`}
                                  className={`${styles.underline} ${styles.blue} ${activeLink === MY_PAGE_COMPONENT ? styles.active : ""}`}
                                  onClick={() => handleLink(MY_PAGE_COMPONENT)}
                            >내정보관리</Link>
                        </Accordion.Body>
                    </Accordion.Item>

                    {isAdmin &&
                        <>
                            <Accordion.Item eventKey="3" className={styles.accordion}>
                                <Accordion.Header className={`${styles.menu} ${styles.mng}`}>
                                    <img src={require("../../../IMAGES/process.png")}/>
                                    <p>문서관리</p>
                                </Accordion.Header>
                                <Accordion.Body onClick={menuName}>
                                    <Link to={`/page/${DOCUMENT_REGISTRATION_COMPONENT}`}
                                          className={`${styles.underline} ${styles.orange} ${activeLink === DOCUMENT_REGISTRATION_COMPONENT ? styles.active : ""}`}
                                          onClick={() => handleLink(DOCUMENT_REGISTRATION_COMPONENT)}
                                    >양식관리</Link>
                                </Accordion.Body>
                                <Accordion.Body onClick={menuName}>
                                    <Link to={`/page/${ALL_DOCUMENT_LIST_COMPONENT}`}
                                          className={`${styles.underline} ${styles.orange} ${activeLink === ALL_DOCUMENT_LIST_COMPONENT ? styles.active : ""}`}
                                          onClick={()=> handleLink(ALL_DOCUMENT_LIST_COMPONENT)}
                                    >결재문서</Link>
                                </Accordion.Body>
                            </Accordion.Item>

                            <Accordion.Item eventKey="4" className={`${styles.accordion} ${styles.mng}`}>
                                <Accordion.Header className={`${styles.menu} ${styles.mng}`}>
                                    <img src={require("../../../IMAGES/management.png")}/>
                                    <p>계정관리</p>
                                </Accordion.Header>
                                <Accordion.Body onClick={menuName}>
                                    <Link to={`/page/${ACCOUNT_REGISTRATION_COMPONENT}`}
                                          className={`${styles.underline} ${styles.orange} ${activeLink === ACCOUNT_REGISTRATION_COMPONENT ? styles.active : ""}`}
                                          onClick={() => handleLink(ACCOUNT_REGISTRATION_COMPONENT)}
                                    >계정등록</Link>
                                </Accordion.Body>
                                <Accordion.Body onClick={menuName}>
                                    <Link to={`/page/${ACCOUNT_MANAGEMENT_COMPONENT}`}
                                          className={`${styles.underline} ${styles.orange} ${activeLink === ACCOUNT_MANAGEMENT_COMPONENT ? styles.active : ""}`}
                                          onClick={() => handleLink(ACCOUNT_MANAGEMENT_COMPONENT)}
                                    >계정관리</Link>
                                </Accordion.Body>
                            </Accordion.Item>
                        </>
                    }
                </Accordion>
            </div>
        </div>
    )
}

export default Sidebar


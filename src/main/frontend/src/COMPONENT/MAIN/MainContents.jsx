import MenuPage from "./MENU_ICON/MenuPage";
import Profile from "./Profile";
import ApprovalNotice from "./ApprovalNotice";
import Notice from "./Notice";
import styles from "./MainContents.module.css"
import Header from "../LAYOUT/Header";
import Calender from "./Calender";
import {useCookies} from "react-cookie";
import {ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE} from "../../constants/constants";
import {useEffect} from "react";
import {useNavigate} from "react-router-dom";
import {LOGIN_COMPONENT} from "../../constants/component_constants";

function MainContents() {

    const [accessCookie, , ] = useCookies([ACCESS_TOKEN_COOKIE]);
    const [refreshCookie, , ] = useCookies([REFRESH_TOKEN_COOKIE]);
    const navigate = useNavigate();

    useEffect(()=>{
        if (!(accessCookie && refreshCookie)){
            navigate(LOGIN_COMPONENT)
        }
    },[])

    return (
        <div className={styles.wrapper}>
            <Header/>
            <div className={styles.contents}>
                    <div className={styles.upperContents}>
                        <Profile/>
                        <ApprovalNotice/>
                        <Notice/>
                    </div>
                    <div className={styles.lowerContents}>
                        <MenuPage/>
                        <Calender/>
                    </div>
            </div>
        </div>
    )
}

export default MainContents
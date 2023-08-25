import styles from "./Profile.module.css"
import LogoutBtn from "../LogoutBtn";
import useStore from "../../store";

function Profile() {
    const { myAccount} = useStore(state => state)

    const isAdmin = myAccount.authority === "ADMIN"

    return (
        <div className={styles.profile}>
            <div className={styles.hello}>
                <p>안녕하세요,</p> <p style={{fontWeight:"bold"}}>{myAccount.name}</p><p>님</p>
            </div>
            <div className={`${styles.container} ${isAdmin ? styles.admin : ""}`}>
                <div className={`${styles.profileImg} ${isAdmin ? styles.adminProfileImg : ""}`}>
                    <img src={require("../../IMAGES/profile.jpg")}/>
                </div>
                <div className={styles.info}>
                    <p>{myAccount.name} {myAccount.position}</p>
                    <p>부서 : {myAccount.team}</p>
                    <p>사번 : {myAccount.no}</p>
                    <LogoutBtn/>
                </div>
            </div>
        </div>
    )
}

export default Profile
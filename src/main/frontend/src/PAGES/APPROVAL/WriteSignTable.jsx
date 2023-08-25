import styles from "./Write.module.css"
import useStore from "../../store";



function WriteSignTable() {
    const {myAccount, signLine} = useStore(state => state)

    const time = new Date();
    const toDay = {
        year: time.getFullYear(),
        month: (time.getMonth() + 1).toString().padStart(2, "0"),
        day: time.getDate().toString().padStart(2, "0")
    }

    const sign_Table_Left_data = [
        {title: "기안자", content: `${myAccount.name}`},
        {title: "기안부서", content: `${myAccount.team}`},
        {title: "기안일", content: `${toDay.year}-${toDay.month}-${toDay.day}`},
        {title: "문서번호", content: ""}
    ]
    const sign_Table_Right_data = [
        {
            signTurn: "작 성",
            sign: "",
            signName: `${myAccount.name}${myAccount.position}`
        },
        {
            signTurn: "검 토",
            sign: "",
            signName: signLine.signTurn1 ? `${signLine.signTurn1.name} ${signLine.signTurn1.position}` : ""
        },
        {
            signTurn: "승 인",
            sign: "",
            signName: signLine.signTurn2 ? `${signLine.signTurn2.name} ${signLine.signTurn2.position}` : ""
        }
    ]

    return (
        <div className={styles.signTable}>
            <div>
                <table>
                    <tbody>
                    {sign_Table_Left_data.map((data) => {
                        return (
                            <tr>
                                <td className={styles.signTableLeft_title}>{data.title}</td>
                                <td className={styles.signTableLeft_content}>{data.content}</td>
                            </tr>
                        )
                    })}
                    </tbody>
                </table>
            </div>
            <div>
                <table>
                    <tbody>
                    <tr>
                        <th className={styles.signTableRight_title}>결재</th>
                        {sign_Table_Right_data.map((data, index) => {
                            return (
                                <td key={index} className={styles.signTableRight_content}>
                                    <div className={styles.signInfo}>{data.signTurn}</div>
                                    <div className={styles.sign}>{data.sign}</div>
                                    <div className={styles.signInfo}>{data.signName}</div>
                                </td>
                            )
                        })}
                    </tr>
                    </tbody>
                </table>

                <div className={styles.referTable}>
                    <div className={styles.referTitle}>참조</div>
                    <div className={styles.referContent}>
                        {signLine.signRefer ? `${signLine.signRefer.name} ${signLine.signRefer.position}` : null}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default WriteSignTable
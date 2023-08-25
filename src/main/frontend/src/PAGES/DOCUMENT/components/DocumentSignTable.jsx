import styles from "../../APPROVAL/Write.module.css"

function DocumentSignTable({documentData, signLine}) {
    const writer = documentData.writer;
    const signTurn1 = signLine[1];
    const signTurn2 = signLine[2];
    const signRefer = signLine[3];

    const sign_Table_Left_data = [
        {title: "기안자", content: `${writer?.name || ""}`},
        {title: "기안부서", content: `${writer?.team || ""}`},
        {title: "기안일", content: `${documentData.createDate || ""}`},
        {title: "문서번호", content: `${documentData.dno}`}
    ]

    const approvalState = (index) => signLine[index]?.status

    const sign_Table_Right_data = [
        {
            signTurn: "작 성",
            sign: approvalState(0) === "승인" ? require("../../../IMAGES/approval.png") :
                approvalState(0) === "반려" ? require("../../../IMAGES/return.png") : null,
            signName: `${writer?.name || ""}${writer?.position || ""}`
        },
        {
            signTurn: "검 토",
            sign: approvalState(1) === "승인" ? require("../../../IMAGES/approval.png") :
                approvalState(1) === "반려" ? require("../../../IMAGES/return.png") : null,
            signName: signTurn1 ? `${signTurn1?.name} ${signTurn1?.position || ""}` : ""
        },
        {
            signTurn: "승 인",
            sign: approvalState(2) === "승인" ? require("../../../IMAGES/approval.png") :
                approvalState(2) === "반려" ? require("../../../IMAGES/return.png") : null,
            signName: signTurn2 ? `${signTurn2?.name} ${signTurn2?.position || ""}` : ""
        }
    ]

    return (
        <div className={styles.signTable}>
            <div style={{marginRight:"30px"}}>
                <table>
                    <tbody>
                    {sign_Table_Left_data.map((data, index) => {
                        return (
                            <tr key={index}>
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
                                    <div className={styles.signInfo}> {data.signTurn} </div>
                                    {data.sign !== null && (
                                        <div className={styles.sign}><img src={data.sign} alt="Sign"/></div>)}
                                    {data.sign === null && (<div className={styles.sign}></div>)}
                                    <div className={styles.signInfo}> {data.signName} </div>
                                </td>
                            )
                        })}
                    </tr>
                    </tbody>
                </table>

                <div className={styles.referTable}>
                    <div className={styles.referTitle}>참조</div>
                    <div className={styles.referContent}>
                        {signRefer ? `${signRefer?.name} ${signRefer?.position}` : null}
                    </div>
                </div>
            </div>
        </div>
    )
}

export default DocumentSignTable
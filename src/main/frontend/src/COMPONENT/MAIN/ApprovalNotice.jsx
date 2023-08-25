import styles from "./ApprovalNotice.module.css"
import tableStyles from "../../PAGES/DOCUMENT/components/DocumentTable.module.css"
import fetcher from "../../fetcher";
import {STANDBY_APPROVAL_LIST_API} from "../../constants/api_constans";
import {useEffect, useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {backgroundColor} from "../../PAGES/DOCUMENT/components/DocumentTable"
import {DOCUMENT_DETAIL_COMPONENT, RECEIVE_DOCUMENT_COMPONENT} from "../../constants/component_constants";

function ApprovalNotice() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetcher.get(STANDBY_APPROVAL_LIST_API)
            .then((res) => setData(res.data))
    }, [])

    const navigate = useNavigate();
    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }

    const getBackgroundColor = (result) => {
        return backgroundColor[result] || "#ffffff"
    };

    return (
        <div className={styles.wrapper}>
            <div className={styles.title}>
                <p className={styles.titleText}>결재 대기 문서</p>
            </div>

            <div className={styles.container}>
                <div className={styles.tableBody}>
                    <table>
                        <colgroup>
                            <col style={{width: "18%"}}/>
                            <col style={{width: "24%"}}/>
                            <col style={{width: "43%"}}/>
                            <col style={{width: "20%"}}/>
                        </colgroup>
                        <tbody>
                        {data?.map((data) => {
                            return (
                                <tr key={data.id} onClick={() => routeDetail(data.id)}>
                                    <td>{data.createDate}</td>
                                    <td>[{data.template.category}]</td>
                                    <td>{data.title}</td>
                                    <td style={{display: "flex", justifyContent: "center"}}>
                                        <div className={tableStyles.stateButton}
                                             style={{background: getBackgroundColor(data.result)}}
                                        >{data.result}</div>
                                    </td>
                                </tr>
                            )
                        })}
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    )
}

export default ApprovalNotice
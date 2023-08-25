import styles from "./DocumentTable.module.css";
import {Table} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {TEMP_DOCUMENT_COMPONENT} from "../../../constants/component_constants";

function TempDocumentTable({listData}) {
    const navigate = useNavigate();

    const routeTempDetail = (id) => {
        navigate(`/page/${TEMP_DOCUMENT_COMPONENT}/${id}`);
    }

    return (
        <div>
            <Table hover className={styles.table}>
                <thead className={styles.tableHead}>
                <tr>
                    <th>NO</th>
                    <th>제목</th>
                    <th>작성일</th>
                </tr>
                </thead>

                <tbody className={styles.tableBody}>
                {listData?.reverse().map((data, index) => {
                    return (
                        <tr key={data.id} onClick={() => routeTempDetail(data.id)}>
                            <td>{index + 1}</td>
                            <td>{data.title}</td>
                            <td>{data.createDate}</td>
                        </tr>
                    )
                })}
                </tbody>
            </Table>
        </div>

    )
}

export default TempDocumentTable
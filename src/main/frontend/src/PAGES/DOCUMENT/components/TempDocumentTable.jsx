import {Table} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {TEMP_DOCUMENT_COMPONENT} from "../../../constants/component_constants";
import {TableBody, TableHead} from "./DocumentTable";

function TempDocumentTable({listData}) {
    const navigate = useNavigate();

    const routeTempDetail = (id) => {
        navigate(`/page/${TEMP_DOCUMENT_COMPONENT}/${id}`);
    }

    return (
        <div>
            <Table hover>
                <TableHead>
                <tr>
                    <th>NO</th>
                    <th>제목</th>
                    <th>작성일</th>
                </tr>
                </TableHead>

                <TableBody>
                {listData?.reverse().map((data, index) => {
                    return (
                        <tr key={data.id} onClick={() => routeTempDetail(data.id)}>
                            <td>{index + 1}</td>
                            <td>{data.title}</td>
                            <td>{data.createDate}</td>
                        </tr>
                    )
                })}
                </TableBody>
            </Table>
        </div>

    )
}

export default TempDocumentTable
import {Table} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {DOCUMENT_DETAIL_COMPONENT} from "../../../constants/component_constants";
import StateButton from "../../../COMPONENT/StateButton";
import styled from "styled-components";

function DocumentTable({listData, offset, limit}) {
    const navigate = useNavigate();

    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }

    return (
        <div>
            <Table hover>
                <TableHead>
                <tr>
                    <th>NO</th>
                    <th>제목</th>
                    <th>기안일</th>
                    <th>상태</th>
                </tr>
                </TableHead>

                <TableBody>
                {listData?.reverse().slice(offset, offset + limit).map((data, index) => {
                    return (
                        <tr key={data.id} onClick={() => routeDetail(data.id)}>
                            <td>{index + 1}</td>
                            <td>{data.title}</td>
                            <td>{data.createDate}</td>
                            <StateButton state={data.result}/>
                        </tr>
                    )
                })}
                </TableBody>
            </Table>
        </div>
    )
}
export default DocumentTable

export const TableHead = styled.thead`
th{
  color: #4429f2;
  font-weight: bold;
  font-size: 23px;
  background: rgba(68, 41, 242, 0.11);
  text-align: center;
}
`
export const TableBody = styled.tbody`
  cursor: pointer;
  text-align: center;
`
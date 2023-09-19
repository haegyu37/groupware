import {Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import {ALL_DOCUMENT_LIST_API} from "../../constants/api_constans";
import fetcher from "../../fetcher";
import {useNavigate} from "react-router-dom";
import {DOCUMENT_DETAIL_COMPONENT, FORBIDDEN_COMPONENT} from "../../constants/component_constants";
import Pagination from "../../COMPONENT/Pagination";
import StateButton from "../../COMPONENT/StateButton";
import styled from "styled-components";
import useStore from "../../store";

function AllDocument() {
    const [listData, setListData] = useState([]);
    // 페이지네이션
    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    const navigate = useNavigate();
    const {myAccount} = useStore(state => state);
    const isAdmin = myAccount?.authority === "ADMIN";

    useEffect(()=>{
        if(!isAdmin) {
            navigate(FORBIDDEN_COMPONENT);
        }
    },[])

    useEffect(() => {
        fetcher.get(ALL_DOCUMENT_LIST_API)
            .then((res) => setListData(res.data))
    }, [])

    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }

    return (
        <Wrapper>
            <Container>
                <TableContainer>
                    <Table hover>
                        <colgroup>
                            <col style={{width: "10%"}}/>
                            <col style={{width: "20%"}}/>
                            <col style={{width: "40%"}}/>
                            <col style={{width: "15%"}}/>
                            <col style={{width: "15%"}}/>
                        </colgroup>
                        <TableHead>
                            <tr>
                                <th>NO</th>
                                <th>기안자</th>
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
                                        <td>{data.writer.name} {data.writer.position}</td>
                                        <td>{data.title}</td>
                                        <td>{data.createDate}</td>
                                        <StateButton state={data.result}/>
                                    </tr>
                                )
                            })}
                        </TableBody>
                    </Table>
                </TableContainer>
                <Pagination total={listData.length} limit={limit} page={page} setPage={setPage}/>
            </Container>
        </Wrapper>
    )
}
export default AllDocument

const Wrapper = styled.div`
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
`
const Container = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 100px;
`
const TableContainer = styled.div`
  height: 600px;
  width: 65%;
  min-width: 800px;
  margin: 0;
`
const TableHead = styled.thead`
  th {
    color: #fb5a2d;
    font-weight: bold;
    font-size: 23px;
    background: rgba(250, 62, 12, 0.15);
    text-align: center;
  }
`
const TableBody = styled.tbody`
  td {
    cursor: pointer;
    text-align: center;
  }
`
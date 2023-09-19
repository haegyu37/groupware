import fetcher from "../../../fetcher";
import {STANDBY_APPROVAL_LIST_API} from "../../../constants/api_constans";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {DOCUMENT_DETAIL_COMPONENT, LOGIN_COMPONENT} from "../../../constants/component_constants";
import styled from "styled-components";
import StateButton from "../../StateButton";
import Swal from "sweetalert2";

function ApprovalNotice() {
    const [data, setData] = useState([]);

    useEffect(() => {
        fetcher.get(STANDBY_APPROVAL_LIST_API)
            .then((res) => setData(res.data))
            .catch((err) => {
                if (err.response && err.response.status === 401) {
                    Swal.fire({
                        title: "로그인이 필요합니다.",
                        icon: 'warning',
                    }).then(() => {
                        navigate(LOGIN_COMPONENT);
                    })
                }
            })
    }, [])

    const navigate = useNavigate();
    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }

    return (
        <Wrapper>
            <Title>
                <p>결재 대기 문서</p>
            </Title>

            <Contents>
                <TableBody>
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
                                    <StateButton state={data.result}/>
                                </tr>
                            )
                        })}
                        </tbody>
                    </table>
                </TableBody>
            </Contents>
        </Wrapper>
    )
}

export default ApprovalNotice

const Wrapper = styled.div`
  border: solid 1px rgba(68, 41, 242, 0.6);
  border-radius: 15px;
  width: 25%;
  min-width: 600px;
  height: 350px;
  margin: 30px;
`
const Title = styled.div`
  justify-content: space-between;
  border-bottom: solid 1px rgba(68, 41, 242, 0.6);
  background: rgba(125, 121, 242, 0.2);
  border-top-left-radius: 15px;
  border-top-right-radius: 15px;
  padding: 5px 20px;
  font-size: 24px;
  font-weight: 600;
`
const Contents = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 20px;
  overflow: auto;
`
const TableBody = styled.div`
  width: 100%;
  cursor: pointer;

  td {
    margin-bottom: 10px;
  }
`
import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {RECEIVE_DOCUMENT_LIST_API} from "../../constants/api_constans";
import {useNavigate} from "react-router-dom";
import {DOCUMENT_DETAIL_COMPONENT} from "../../constants/component_constants";
import Pagination from "../../COMPONENT/Pagination";
import StateButton, {backgroundColor} from "../../COMPONENT/StateButton";
import styled from "styled-components";

function ReceiveDocument() {
    const navigate = useNavigate();
    const [data, setData] = useState([]);

    // 페이지네이션
    const [limit, setLimit] = useState(5);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    const cardColor = {
        "결재대기": "#fdfaf1",
        "진행중": "#f2fdff",
        "승인": "#f0fff0",
        "반려": "#fff1f3"
    }

    useEffect(() => {
        fetcher.get(RECEIVE_DOCUMENT_LIST_API)
            .then((res) => setData(res.data))
    }, [])

    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }

    const getBackgroundColor = (state) => {
        return backgroundColor[state] || "#ffffff"
    };

    return (
        <Wrapper>
            <CardContainer>
                {data?.reverse().slice(offset, offset + limit).map((data) => {
                    return (
                        <Card key={data.id} background={cardColor[data.result]}>
                            <DocumentInfo>
                                <CardUpper>
                                    <CardTitle>{data.title}</CardTitle>
                                    <StateButton state={data.result}/>
                                </CardUpper>
                                <CardLower>
                                    <div>기안자 : {data.writer.name} {data.writer.position}</div>
                                    <div>날짜 : {data.createDate}</div>
                                    <div>양식 : {data.template.category}</div>
                                </CardLower>
                            </DocumentInfo>

                            <DivisionLine/>

                            <ApprovalButton
                                style={{
                                    cursor: "pointer",
                                    color: data.result === "승인" || data.result === "반려" ? "gray" : ""
                                }}
                                onClick={() => routeDetail(data.id)}
                            >
                                {data.result === "승인" || data.result === "반려" ? "상세보기" : "결재하기"}
                            </ApprovalButton>
                        </Card>
                    )
                })}
            </CardContainer>

            <Pagination total={data.length} limit={limit} page={page} setPage={setPage}/>
        </Wrapper>
    )
}

export default ReceiveDocument

const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
`
const CardContainer = styled.div`
  padding-top: 80px;
  height: 930px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`
const Card = styled.div`
  min-width: 800px;
  width: 45%;
  height: 140px;
  border: 1.5px solid #afb0b1;
  border-radius: 15px;
  display: flex;
  flex-direction: row;
  margin-bottom: 25px;
  background: ${props => props.background};
`
const DocumentInfo = styled.div`
  height: 100%;
  padding: 10px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  width: 85%;
`
const CardUpper = styled.div`
  display: flex;
  flex-direction: row;
`
const CardTitle = styled.div`
  font-size: 22px;
  font-weight: 600;
  margin: 0 20px;
`
const CardLower = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  color: grey;
  padding: 0 20px;
`
const DivisionLine = styled.div`
  border-right: 1.5px solid #afb0b1;
  height: 110px;
  margin-top: 15px;
`
const ApprovalButton = styled.div`
  width: 15%;
  margin: auto;
  text-align: center;
`
import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {
    APPROVED_DOCUMENT_LIST_API, ONGOING_DOCUMENT_LIST_API,
    REJECTED_DOCUMENT_LIST_API, REPORT_DOCUMENT_LIST_API
} from "../../constants/api_constans";
import DocumentTable from "./components/DocumentTable";
import {Button, FormControl, InputGroup} from "react-bootstrap";
import ButtonGroup from "./components/ButtonGroup";
import Pagination from "../../COMPONENT/Pagination";
import styled from "styled-components";

function ReportDocument() {
    const [listData, setListData] = useState([]);
    const [ongoing, setOngoing] = useState([]);
    const [approved, setApproved] = useState([]);
    const [rejected, setRejected] = useState([]);
    const [activeBtn, setActiveBtn] = useState("all");
    // 페이지네이션
    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        fetchAllDocument()
    }, []);

    // 버튼에 따라 데이터 필터링
    const fetchAllDocument = () => {
        fetcher.get(REPORT_DOCUMENT_LIST_API)
            .then((res) => setListData(res.data))
    }
    const filterOngoing = () => {
        fetcher.get(ONGOING_DOCUMENT_LIST_API)
            .then((res) => setOngoing(res.data));
    }
    const filterApproved = () => {
        fetcher.get(APPROVED_DOCUMENT_LIST_API)
            .then((res) => setApproved(res.data));
    }
    const filterRejected = () => {
        fetcher.get(REJECTED_DOCUMENT_LIST_API)
            .then((res) => setRejected(res.data));
    }

    useEffect(() => {
        switch (activeBtn) {
            case "all":
                fetchAllDocument()
                break;
            case "ongoing":
                filterOngoing()
                break;
            case "approved":
                filterApproved()
                break;
            case "rejected":
                filterRejected()
                break;
            default:
                fetchAllDocument()
                break;
        }
    }, [activeBtn])

    return (
        <Wrapper>
            <ButtonContainer>
                <ButtonGroup setActiveBtn={setActiveBtn}/>
            </ButtonContainer>

            <TableContainer>
                <Table>
                    <DocumentTable
                        listData={
                            activeBtn === "all" ? listData : activeBtn === "ongoing" ? ongoing
                                : activeBtn === "approved" ? approved : activeBtn === "rejected" ? rejected : listData
                        }
                        limit={limit} offset={offset}
                    />
                </Table>
                <Pagination total={listData.length} limit={limit} page={page} setPage={setPage}/>
            </TableContainer>
        </Wrapper>
    )
}
export default ReportDocument

export const Wrapper = styled.div`
  height: 100%;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
`
const ButtonContainer = styled.div`
  height: 15%;
  display: flex;
  flex-direction: column;
  justify-content: space-evenly;
`
export const TableContainer = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
`
export const Table = styled.div`
  height: 500px;
  width: 65%;
  min-width: 800px;
`
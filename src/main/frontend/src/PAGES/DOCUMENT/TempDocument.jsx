import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {TEMP_DOCUMENT_LIST_API} from "../../constants/api_constans";
import TempDocumentTable from "./components/TempDocumentTable";
import Pagination from "../../COMPONENT/Pagination";
import {Table, TableContainer, Wrapper} from "./ReportDocument";

function TempDocument() {
    const [listData, setListData] = useState([]);
    // 페이지네이션
    const [limit, setLimit] = useState(10);
    const [page, setPage] = useState(1);
    const offset = (page - 1) * limit;

    useEffect(() => {
        fetcher.get(TEMP_DOCUMENT_LIST_API)
            .then((res) => setListData(res.data))
    }, []);

    return (
        <Wrapper>
            <TableContainer style={{marginTop:"200px"}}>
                <Table>
                    <TempDocumentTable listData={listData} limit={limit} offset={offset}/>
                </Table>
                <Pagination total={listData.length} limit={limit} page={page} setPage={setPage}/>
            </TableContainer>
        </Wrapper>
    )
}

export default TempDocument

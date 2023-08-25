import styles from "./ReportDocument.module.css"
import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {
    APPROVED_DOCUMENT_LIST_API, ONGOING_DOCUMENT_LIST_API,
    REJECTED_DOCUMENT_LIST_API, REPORT_DOCUMENT_LIST_API
} from "../../constants/api_constans";
import DocumentTable from "./components/DocumentTable";
import {Button, FormControl, InputGroup} from "react-bootstrap";
import ButtonGroup from "./components/ButtonGroup";

function ReportDocument() {
    const [listData, setListData] = useState([]);
    const [ongoing, setOngoing] = useState([]);
    const [approved, setApproved] = useState([]);
    const [rejected, setRejected] = useState([]);
    const [activeBtn, setActiveBtn] = useState("all");

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
            case "all":fetchAllDocument()
                break;
            case "ongoing":filterOngoing()
                break;
            case "approved":filterApproved()
                break;
            case "rejected":filterRejected()
                break;
            default:fetchAllDocument()
                break;
        }
    }, [activeBtn])

    return (
        <div className={styles.wrapper}>
            <div className={styles.buttonContainer}>
                <ButtonGroup setActiveBtn={setActiveBtn} />
            </div>

            <div className={styles.search}>
                <InputGroup className="mb-3">
                    <FormControl type="text" className="form-control-lg" placeholder="제목"/>
                    <Button className={styles.searchButton}> 검색 </Button>
                </InputGroup>
            </div>
            <div className={styles.tableContainer}>
                <div className={styles.table}>
                    <DocumentTable listData={activeBtn==="all"?listData : activeBtn==="ongoing"?ongoing
                        : activeBtn==="approved"?approved : activeBtn==="rejected"?rejected : listData}
                    />
                </div>
            </div>
        </div>
    )
}

export default ReportDocument
import styles from "./ReceiveDocument.module.css"
import {useEffect, useState} from "react";
import {Button, FormControl, InputGroup} from "react-bootstrap";
import {backgroundColor} from "./components/DocumentTable";
import fetcher from "../../fetcher";
import {RECEIVE_DOCUMENT_LIST_API} from "../../constants/api_constans";
import {useNavigate} from "react-router-dom";
import {DOCUMENT_DETAIL_COMPONENT} from "../../constants/component_constants";

function ReceiveDocument() {
    const navigate = useNavigate();
    const [data, setData] = useState([]);

    useEffect(()=>{
        fetcher.get(RECEIVE_DOCUMENT_LIST_API)
            .then((res)=> setData(res.data))
    },[])

    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }

    const getBackgroundColor = (state) => {
        return backgroundColor[state] || "#ffffff"
    };

    return (
        <div className={styles.wrapper}>
            <div className={styles.search}>
                <InputGroup>
                    <FormControl type="text" className="form-control-lg" placeholder="제목"/>
                    <Button className={styles.searchButton}> 검색 </Button>
                </InputGroup>
            </div>
            <div className={styles.cardContainer}>
                {data?.reverse().map((data) => {
                    return (
                        <div className={styles.card} key={data.id}>
                            <div className={styles.contents}>
                                <div className={styles.upperState}
                                     style={{background: getBackgroundColor(data.result)}}
                                >
                                    {data.result}
                                </div>

                                <div className={styles.documentInfo}>
                                    <div className={styles.cardUpper}>
                                        <div className={styles.cardTitle}>{data.title}</div>
                                    </div>
                                    <div className={styles.cardLower}>
                                        <div>기안자 : {data.writer.name} {data.writer.position}</div>
                                        <div>날짜 : {data.createDate}</div>
                                        <div>양식 : {data.template.category}</div>
                                    </div>
                                </div>
                            </div>

                            <div className={styles.divisionLine}/>

                            <div className={styles.approvalButton}
                                 style={{
                                     cursor: "pointer",
                                     color: data.result === "승인" || data.result === "반려" ? "gray" : ""
                                 }}
                                 onClick={()=>routeDetail(data.id)}
                            >
                                {data.result === "승인" || data.result === "반려" ? "상세보기" : "결재하기"}
                            </div>
                        </div>
                    )
                })}
            </div>
        </div>
    )
}

export default ReceiveDocument
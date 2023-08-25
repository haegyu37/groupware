import styles from  "./AllDocument.module.css"
import {Button, FormControl, InputGroup, Table} from "react-bootstrap";
import {backgroundColor} from "../DOCUMENT/components/DocumentTable";
import {useEffect, useState} from "react";
import {ALL_DOCUMENT_LIST_API} from "../../constants/api_constans";
import fetcher from "../../fetcher";
import {useNavigate} from "react-router-dom";
import {DOCUMENT_DETAIL_COMPONENT} from "../../constants/component_constants";

function AllDocument() {
    const [listData, setListData] =useState([]);

    useEffect(()=>{
        fetcher.get(ALL_DOCUMENT_LIST_API)
            .then((res)=> setListData(res.data))
    },[])

    const navigate = useNavigate();
    const routeDetail = (id) => {
        navigate(`/page/${DOCUMENT_DETAIL_COMPONENT}/${id}`);
    }
    const getBackgroundColor = (result) => {
        return backgroundColor[result] || "#ffffff"
    };

    return(
        <div className={styles.wrapper}>
            <div className={styles.container}>
                <div className={styles.search}>
                    <InputGroup className="mb-3">
                        <FormControl type="text" className="form-control-lg" placeholder="제목"/>
                        <Button className={styles.searchButton}> 검색 </Button>
                    </InputGroup>
                </div>

                <div className={styles.table}>
                    <Table hover>
                        <colgroup>
                            <col style={{width:"10%"}} />
                            <col style={{width:"25%"}} />
                            <col style={{width:"45%"}} />
                            <col style={{width:"10%"}} />
                            <col style={{maxWidth:"100px"}} />
                        </colgroup>
                        <thead className={styles.tableHead}>
                        <tr>
                            <th>NO</th>
                            <th>기안자</th>
                            <th>제목</th>
                            <th>기안일</th>
                            <th>상태</th>
                        </tr>
                        </thead>

                        <tbody className={styles.tableBody}>
                        {listData?.reverse().map((data, index) => {
                            return (
                                <tr key={data.id} onClick={() => routeDetail(data.id)}>
                                    <td>{index + 1}</td>
                                    <td>{data.writer.name} {data.writer.position}</td>
                                    <td>{data.title}</td>
                                    <td>{data.createDate}</td>
                                    <td style={{display: "flex", justifyContent: "center"}}>
                                        <div className={styles.stateButton}
                                             style={{background: getBackgroundColor(data.result)}}
                                        >{data.result}</div>
                                    </td>
                                </tr>
                            )
                        })}
                        </tbody>
                    </Table>
                </div>
            </div>
        </div>
    )
}

export default AllDocument
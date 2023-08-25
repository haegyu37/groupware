import styles from "./Modal.module.css";
import tableStyles from "./AccountModal.module.css";
import {Button, FormControl, InputGroup, Modal, Table} from "react-bootstrap";
import useStore from "../../../store";
import {useEffect, useState} from "react";
import {MEMBER_LIST_INFO_API} from "../../../constants/api_constans";
import fetcher from "../../../fetcher";

function AccountModal({showAccountModal, handleAccountModalClose, fetchMemberList}) {
    const {selectAccount} = useStore(state => state)
    const [member, setMember] = useState([]);

    useEffect(()=>{
        fetcher.get(MEMBER_LIST_INFO_API)
            .then((res) => setMember(res.data))
    }, [])

    const pickAccount = (account) => {
        selectAccount(account);
        handleAccountModalClose();
    }

    return (
        <div>
            <Modal show={showAccountModal} onHide={handleAccountModalClose}
                   className={`${styles.modal} ${tableStyles.wrap}`} centered
            >
                <Modal.Header closeButton className={styles.modalHeader}>
                    <Modal.Title style={{fontWeight: "bold"}}>계정 선택</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <div>
                        <div className={tableStyles.container}>
                            <div className={tableStyles.tableContainer}>
                                <Table hover>
                                    <thead className={tableStyles.tableHead}>
                                    <tr>
                                        <th>사번</th>
                                        <th>이름</th>
                                        <th>직급</th>
                                        <th>부서</th>
                                    </tr>
                                    </thead>

                                    <tbody className={tableStyles.tableBody}>
                                    {member.map((data, index) => {
                                        return (
                                            <tr key={index} style={{cursor: "pointer"}}
                                                onClick={() => pickAccount(data)}
                                            >
                                                <td>{data.no}</td>
                                                <td>{data.name}</td>
                                                <td>{data.position}</td>
                                                <td>{data.team}</td>
                                            </tr>
                                        )
                                    })}
                                    </tbody>
                                </Table>
                            </div>
                            <div className={tableStyles.lowerContainer}>
                                <div className={tableStyles.search}>
                                    <InputGroup className="mb-3">
                                        <FormControl type="text" className="form-control-lg" placeholder="이름"/>
                                        <Button className="buttonAdmin"> 검색 </Button>
                                    </InputGroup>
                                </div>

                                <Button variant="secondary"
                                        className={styles.button}
                                        onClick={handleAccountModalClose}
                                >취소</Button>
                            </div>
                        </div>
                    </div>

                </Modal.Body>
            </Modal>
        </div>
    )
}

export default AccountModal
import {useEffect, useState} from "react";
import styles from "./Modal.module.css";
import {Button, Modal} from "react-bootstrap";
import useStore from "../../../store";
import fetcher from "../../../fetcher";
import {POSITION_INFO_API} from "../../../constants/api_constans";


function PositionModal({showPositionModal, handlePositionModalClose}) {
    const [position, setPosition] = useState([]);
    const {selectPosition} = useStore(state => state);

    useEffect(() => {
        fetcher.get(POSITION_INFO_API)
            .then(res => setPosition(res.data))
    }, [])

    const pickPosition = (position) => {
        selectPosition(position);
        handlePositionModalClose();
    }

    return (
        <>
            <Modal show={showPositionModal} onHide={handlePositionModalClose} className={styles.modal} centered>
                <Modal.Header closeButton className={styles.modalHeader}>
                    <Modal.Title style={{fontWeight: "bold"}}>직급 선택</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {position.map((data, index) => {
                        return (
                            <p key={index}
                               className={styles.position}
                               style={{cursor: "pointer"}}
                               onClick={() => pickPosition(data)}
                            >{data}</p>
                        )
                    })}

                    <div style={{float: "right"}}>
                        <Button variant="secondary"
                                className={styles.button}
                                onClick={handlePositionModalClose}
                        >취소</Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default PositionModal
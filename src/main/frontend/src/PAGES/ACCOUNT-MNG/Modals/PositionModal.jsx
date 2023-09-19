import {useEffect, useState} from "react";
import {Modal} from "react-bootstrap";
import useStore from "../../../store";
import fetcher from "../../../fetcher";
import {POSITION_INFO_API} from "../../../constants/api_constans";
import {ModalHeader, ModalButton} from "./AccountModal";
import styled from "styled-components";

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
        <StyledModal show={showPositionModal} onHide={handlePositionModalClose} centered>
            <ModalHeader>
                <Modal.Title><p>직급 선택</p></Modal.Title>
            </ModalHeader>
            <Modal.Body>
                {position.map((data, index) => {
                    return (
                        <ListButton key={index} onClick={() => pickPosition(data)}>{data}</ListButton>
                    )
                })}

                <div style={{float: "right"}}>
                    <ModalButton variant="secondary" onClick={handlePositionModalClose}>취소</ModalButton>
                </div>
            </Modal.Body>
        </StyledModal>
    )
}
export default PositionModal
export const StyledModal = styled(Modal)`
  --bs-modal-width: 400px;
  --bs-modal-border-color: #fa3e0c;
  --bs-modal-header-border-color: #fa3e0c;
  font-weight: bold;
`
export const ListButton = styled.p`
  margin: 0 0 10px 0;
  padding: 15px 0;
  font-size: 15px;
  text-align: center;
  border: 1px solid gray;
  border-radius: 10px;
  cursor: pointer;
  &:hover {
    background: rgba(250, 62, 12, 0.08);
  }
`
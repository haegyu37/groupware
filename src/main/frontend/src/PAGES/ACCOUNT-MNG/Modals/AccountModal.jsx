import {Button, Modal, Table} from "react-bootstrap";
import useStore from "../../../store";
import {useEffect, useState} from "react";
import {MEMBER_LIST_INFO_API} from "../../../constants/api_constans";
import fetcher from "../../../fetcher";
import styled from "styled-components";

function AccountModal({showAccountModal, handleAccountModalClose, fetchMemberList}) {
    const {selectAccount} = useStore(state => state)
    const [member, setMember] = useState([]);

    useEffect(() => {
        showAccountModal && fetcher.get(MEMBER_LIST_INFO_API)
            .then((res) => setMember(res.data))
    }, [showAccountModal])

    const pickAccount = (account) => {
        selectAccount(account);
        handleAccountModalClose();
    }

    const accountState = {
        "USER" : "일반",
        "ADMIN" : "관리자",
        "BLOCK" : "접속차단"
    }

    return (
        <StyledModal show={showAccountModal} onHide={handleAccountModalClose} centered>
            <ModalHeader>
                <Modal.Title><p>계정 선택</p></Modal.Title>
            </ModalHeader>
            <Modal.Body>
                <ModalContainer>
                    <Table>
                        <TableHead>
                            <tr>
                                <th>사번</th>
                                <th>이름</th>
                                <th>직급</th>
                                <th>부서</th>
                                <th>계정상태</th>
                            </tr>
                        </TableHead>
                    </Table>
                    <TableContainer>
                        <Table hover>
                            <TableBody>
                                {member.map((data, index) => {
                                    const accountStatus = accountState[data.authority]
                                    return (
                                        <tr key={index} onClick={() => pickAccount(data)}>
                                            <td>{data.no}</td>
                                            <td>{data.name}</td>
                                            <td>{data.position}</td>
                                            <td>{data.team}</td>
                                            <td>{accountStatus}</td>
                                        </tr>
                                    )
                                })}
                            </TableBody>
                        </Table>
                    </TableContainer>
                    <LowerContainer>
                        <ModalButton variant="secondary" onClick={handleAccountModalClose}>취소</ModalButton>
                    </LowerContainer>
                </ModalContainer>
            </Modal.Body>
        </StyledModal>
    )
}

export default AccountModal

const StyledModal = styled(Modal)`
  --bs-modal-width: 600px;
`
export const ModalHeader = styled(Modal.Header)`
  color: white;
  background: #fa3e0c;

  p {
    font-weight: bold;
  }
`
const ModalContainer = styled.div`
  display: flex;
  flex-direction: column;
  margin: 50px auto;
  width: 500px;
`
const TableContainer = styled.div`
  max-height: 350px;
  overflow: auto;
`
const TableHead = styled.thead`
  th {
    color: #fa3e0c;
    font-weight: bold;
    font-size: 23px;
    background: rgba(250, 62, 12, 0.08);
    text-align: center;
  }
`
const TableBody = styled.tbody`
  tr {
    cursor: pointer;
    text-align: center;
  }
`
const LowerContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: baseline
`
export const ModalButton = styled(Button)`
  margin: 20px 10px 10px 0;
`
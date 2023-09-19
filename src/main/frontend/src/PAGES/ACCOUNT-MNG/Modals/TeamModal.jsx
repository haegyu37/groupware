import {Modal} from "react-bootstrap";
import {useEffect, useState} from "react";
import useStore from "../../../store";
import fetcher from "../../../fetcher";
import {TEAM_INFO_API} from "../../../constants/api_constans";
import {ModalHeader, ModalButton} from "./AccountModal";
import {ListButton, StyledModal} from "./PositionModal";

function TeamModal({showTeamModal, handleTeamModalClose}) {

    const [team, setTeam] = useState([]);
    const {selectTeam} = useStore(state => state);

    useEffect(() => {
        fetcher.get(TEAM_INFO_API)
            .then((res) => setTeam(res.data))
    }, []);

    const pickTeam = (team) => {
        selectTeam(team);
        handleTeamModalClose();
    }

    return (
        <StyledModal show={showTeamModal} onHide={handleTeamModalClose} centered>
            <ModalHeader>
                <Modal.Title><p>부서 선택</p></Modal.Title>
            </ModalHeader>

            <Modal.Body>
                {team.map((data, index) => {
                    return (
                        <ListButton key={index} onClick={() => pickTeam(data)}>{data}</ListButton>
                    )
                })}

                <div style={{float: "right"}}>
                    <ModalButton variant="secondary" onClick={handleTeamModalClose}>취소</ModalButton>
                </div>
            </Modal.Body>
        </StyledModal>
    )
}

export default TeamModal
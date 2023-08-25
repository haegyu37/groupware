import {Button, Modal} from "react-bootstrap";
import styles from "./Modal.module.css";
import {useEffect, useState} from "react";
import useStore from "../../../store";
import fetcher from "../../../fetcher";
import {TEAM_INFO_API} from "../../../constants/api_constans";

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
        <>
            <Modal show={showTeamModal} onHide={handleTeamModalClose} className={styles.modal} centered>
                <Modal.Header closeButton className={styles.modalHeader}>
                    <Modal.Title style={{fontWeight: "bold"}}>부서 선택</Modal.Title>
                </Modal.Header>

                <Modal.Body>
                    {team.map((data, index)=>{
                        return(
                            <p  key={index}
                                className={styles.position}
                               style={{cursor:"pointer"}}
                               onClick={()=>pickTeam(data)}
                            >{data}</p>
                        )
                    })}

                    <div style={{float: "right"}}>
                        <Button variant="secondary"
                                className={styles.button}
                                onClick={handleTeamModalClose}
                        >취소</Button>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    )
}

export default TeamModal
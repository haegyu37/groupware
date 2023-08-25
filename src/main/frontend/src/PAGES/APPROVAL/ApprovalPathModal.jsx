import styles from "./ApprovalPathModal.module.css";
import {Button, Modal, Table} from "react-bootstrap";
import {useEffect, useState} from "react";
import fetcher from "../../fetcher";
import {
    APPROVAL_BOOKMARK_CREATE_API,
    APPROVAL_BOOKMARK_DELETE_API,
    APPROVAL_BOOKMARK_INFO_API,
    APPROVAL_BOOKMARK_LIST_API,
    TEAM_INFO_API,
    TEAM_MEMBER_INFO_API
} from "../../constants/api_constans";
import useStore from "../../store";
import Accordion from "react-bootstrap/Accordion";
import Swal from "sweetalert2";

function ApprovalPathModal({showApprovalPathModal, handleApprovalPathModalClose}) {
    const {myAccount, setSignLine} = useStore(state => state)
    const [team, setTeam] = useState([]);
    const [selectTeam, setSelectTeam] = useState([]);
    const [member, setMember] = useState([]);
    const [clickedIndex, setClickedIndex] = useState(null);
    const [activeMember, setActiveMember] = useState(null);
    const [approvalMembers, setApprovalMembers] = useState([]); // 결재자 테이블
    const [referMember, setReferMember] = useState([]);     // 참조자 테이블
    const [bookmarkName, setBookmarkName] = useState("");   // 즐겨찾기 이름
    const [bookmarkId, setBookmarkId] = useState("");   // 즐겨찾기 ID
    const [bookmarkList, setBookmarkList] = useState([]);   // 즐겨찾기리스트

    useEffect(() => {
        fetcher.get(TEAM_INFO_API)
            .then((res) => setTeam(res.data))
        setApprovalMembers([])
        setReferMember([])
    }, [])

    useEffect(() => {
        if (selectTeam) {
            fetcher.get(`${TEAM_MEMBER_INFO_API}/${selectTeam}`)
                .then((res) => setMember(res.data))
        } else {
            setMember([])
        }
    }, [selectTeam])

    const handleMemberClick = (index) => {
        setClickedIndex(index);
        setActiveMember(member[index]);
    };

    // 결재자 테이블 추가삭제
    const addToApprovalTable = () => {
        if (activeMember && approvalMembers.length < 2) {
            setApprovalMembers(approvalMembers.concat(activeMember));
        } else {
            Swal.fire("결재라인은 최대 3명입니다.")
        }
    }
    const removeApprovalTable = () => {
        setApprovalMembers((prevMembers) => prevMembers.slice(0, -1));
    }

    // 참조자 테이블 추가삭제
    const addToReferTable = () => {
        if (activeMember && referMember.length < 1) {
            setReferMember(referMember.concat(activeMember));
        } else {
            Swal.fire("참조자는 한명만 가능합니다.")
        }
    }
    const removeReferTable = () => {
        setReferMember((prevMembers) => prevMembers.slice(0, -1));
    }
    // 결재라인 즐겨찾기 관련
    const fetchBookmark = () => {
        return fetcher.get(APPROVAL_BOOKMARK_LIST_API)
            .then((res) => {
                const fetchData = Object.values(res.data)
                const bookmarkData = fetchData.flatMap((innerArr) => {
                    const firstObj = innerArr[0];
                    return {
                        id: firstObj.lineId,
                        name: firstObj.lineName
                    }
                })
                setBookmarkList(bookmarkData);
            })
    }
    useEffect(() => {
        fetchBookmark()
    }, [])

    const addBookmark = () => {
        fetcher.post(APPROVAL_BOOKMARK_CREATE_API, {
            name: bookmarkName,
            approvers: [approvalMembers[0].id, approvalMembers[1].id, referMember[0]?.id || null]
        })
            .then(() => {
                Swal.fire({
                    position: 'mid',
                    icon: 'success',
                    title: '결재라인 저장 완료',
                    showConfirmButton: false,
                    timer: 1500
                })
                setBookmarkName("")
                fetchBookmark()
            })
    }
    const removeBookmark = (id) => {
        fetcher.delete(`${APPROVAL_BOOKMARK_DELETE_API}/${id}`)
            .then(() => {
                    Swal.fire({
                        position: 'mid',
                        icon: 'success',
                        title: '결재라인 삭제 완료',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    fetchBookmark()
                }
            )
    }
    const bookmarkInfo = (id) => {
        fetcher.get(`${APPROVAL_BOOKMARK_INFO_API}/${id}`)
            .then((res) => {
                const fetchData = (res.data)
                const key = Object.keys(fetchData)
                setApprovalMembers([fetchData[key][1], fetchData[key][2]]);
                setReferMember([fetchData[key][3]]);
            })
    }

    // 결재라인 적용
    const enterSignLine = () => {
        setSignLine({
            signTurn1: {
                id: approvalMembers[0].id,
                name: approvalMembers[0].name,
                position: approvalMembers[0].position
            },
            signTurn2: {
                id: approvalMembers[1].id,
                name: approvalMembers[1].name,
                position: approvalMembers[1].position
            },
            signRefer: referMember[0] ? {
                id: referMember[0].id,
                name: referMember[0].name,
                position: referMember[0].position
            } : null,
        })
        handleApprovalPathModalClose()
    }

    return (
        <div>
            <Modal show={showApprovalPathModal} onHide={handleApprovalPathModalClose}
                   className={`${styles.modal} ${styles.wrap}`} centered
            >
                <Modal.Header closeButton className={styles.modalHeader}>
                    <Modal.Title style={{fontWeight: "bold"}}>결재선 지정</Modal.Title>
                </Modal.Header>
                <Modal.Body style={{padding: "0"}}>
                    <div className={styles.contents}>
                        <div className={styles.leftSide}>
                            <div className={styles.title}>조직도</div>
                            <Accordion defaultActiveKey="0">
                                {team?.map((teamData, index) => {
                                    return (
                                        <Accordion.Item key={index} eventKey={index} className={styles.accordion}>
                                            <Accordion.Header onClick={() => setSelectTeam(teamData)}>
                                                <img src={require("../../IMAGES/members.png")}
                                                     style={{padding: "0 10px"}}/>
                                                {teamData}
                                            </Accordion.Header>

                                            {member?.map((memberData, memberIndex) => {
                                                return (
                                                    <Accordion.Body key={memberIndex}
                                                                    onClick={() => handleMemberClick(memberIndex)}
                                                                    className={`${styles.chartMember} ${memberIndex === clickedIndex ? styles.boldText : ""}`}
                                                    >
                                                        <img src={require("../../IMAGES/member.png")}
                                                             style={{padding: "0 12px"}}/>
                                                        {memberData.name} {memberData.position}
                                                    </Accordion.Body>
                                                )
                                            })}
                                        </Accordion.Item>
                                    )
                                })}
                            </Accordion>
                        </div>

                        <div className={styles.midSide}>
                            <div className={styles.buttonGroup}>
                                <p className={styles.title}>결재자</p>
                                <Button className={styles.arrowButton} onClick={addToApprovalTable}>
                                    <img src={require("../../IMAGES/right-arrow.png")}/>
                                </Button>
                                <Button className={styles.arrowButton} onClick={removeApprovalTable}>
                                    <img src={require("../../IMAGES/left-arrow.png")}/>
                                </Button>
                            </div>

                            <div className={styles.buttonGroup}>
                                <p className={styles.title}>참조자</p>
                                <Button className={styles.arrowButton} onClick={addToReferTable}>
                                    <img src={require("../../IMAGES/right-arrow.png")}/>
                                </Button>
                                <Button className={styles.arrowButton} onClick={removeReferTable}>
                                    <img src={require("../../IMAGES/left-arrow.png")}/>
                                </Button>
                            </div>
                        </div>

                        <div>
                            <div className={styles.rightSide}>
                                <p className={styles.title}>결재선 정보</p>
                                <div className={styles.bookmarkLine}>
                                    <p style={{marginBottom: "0"}}>사용자 결재라인</p>
                                    <select onChange={(e) => {
                                        const id = e.target.value;
                                        bookmarkInfo(id)
                                        setBookmarkId(id)
                                    }}>
                                        <option selected disabled>결재라인 선택</option>
                                        {bookmarkList?.map((data, index) => {
                                            return (
                                                <option key={index} value={data.id}>
                                                    {data.name}
                                                </option>
                                            )
                                        })}
                                    </select>
                                    <Button className="button" style={{padding: "6px"}}
                                            onClick={() => removeBookmark(bookmarkId)}
                                    >삭제</Button>
                                </div>

                                <div>
                                    <div className={styles.tableContainer} style={{height: "220px"}}>
                                        <p className={styles.title}>결재자</p>
                                        <Table>
                                            <thead className={styles.tableHead}>
                                            <tr>
                                                <th>이름</th>
                                                <th>직급</th>
                                                <th>부서</th>
                                                <th>결재선</th>
                                            </tr>
                                            </thead>
                                            <tbody className={styles.tableBody}>
                                            <tr>
                                                <td>{myAccount.name}</td>
                                                <td>{myAccount.position}</td>
                                                <td>{myAccount.team}</td>
                                                <td>작성</td>
                                            </tr>
                                            {approvalMembers?.map((memberData, index) => {
                                                return (
                                                    <tr key={index}>
                                                        <td>{memberData.name}</td>
                                                        <td>{memberData.position}</td>
                                                        <td>{memberData.team}</td>
                                                        <td>{index === 0 ? "검토" : "승인"}</td>
                                                    </tr>
                                                )
                                            })}
                                            </tbody>
                                        </Table>
                                    </div>
                                </div>

                                <div className={styles.tableContainer} style={{height: "140px"}}>
                                    <p className={styles.title}>참조자</p>
                                    <Table>
                                        <thead className={styles.tableHead}>
                                        <tr>
                                            <th>이름</th>
                                            <th>직급</th>
                                            <th>부서</th>
                                        </tr>
                                        </thead>
                                        <tbody className={styles.tableBody}>
                                        {referMember?.map((memberData, index) => {
                                            return (
                                                <tr key={index}>
                                                    <td>{memberData?.name}</td>
                                                    <td>{memberData?.position}</td>
                                                    <td>{memberData?.team}</td>
                                                </tr>
                                            )
                                        })}
                                        </tbody>
                                    </Table>
                                </div>


                                <div className={styles.bookmarkLine}>
                                    <p style={{marginBottom: "0"}}>사용자 결재라인 이름</p>
                                    <input value={bookmarkName}
                                           onChange={(e) => setBookmarkName(e.target.value)}
                                    />
                                    <Button className="button" style={{padding: "6px"}}
                                            onClick={addBookmark}
                                    >저장</Button>
                                </div>
                            </div>

                            <div style={{float: "right", padding: "15px 0"}}>
                                <Button className="button"
                                        onClick={enterSignLine}
                                >적용
                                </Button>
                                <Button variant="secondary"
                                        style={{marginLeft: "15px"}}
                                        onClick={handleApprovalPathModalClose}
                                >닫기</Button>
                            </div>
                        </div>
                    </div>
                </Modal.Body>
            </Modal>
        </div>
    )
}

export default ApprovalPathModal
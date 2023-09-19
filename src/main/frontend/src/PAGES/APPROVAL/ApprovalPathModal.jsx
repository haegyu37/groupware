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
import styled from "styled-components";

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
        if (!id) {
            Swal.fire("결재라인을 선택해 주세요.")
            return
        }
        fetcher.delete(`${APPROVAL_BOOKMARK_DELETE_API}/${id}`)
            .then(() => {
                    Swal.fire({
                        position: 'mid',
                        icon: 'success',
                        title: '결재라인 삭제 완료',
                        showConfirmButton: false,
                        timer: 1500
                    })
                    fetchBookmark().then(() => {
                        setBookmarkId("")
                    })
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
        if (approvalMembers.length < 2) {
            Swal.fire("결재자를 선택해 주세요.")
            return
        }
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
        <ModalWrapper show={showApprovalPathModal} onHide={handleApprovalPathModalClose} centered>
            <ModalHeader closeButton>
                <Modal.Title style={{fontWeight: "bold"}}>결재선 지정</Modal.Title>
            </ModalHeader>
            <Modal.Body>
                <Contents>
                    <LeftSide>
                        <Title>조직도</Title>
                        <Accordion defaultActiveKey="0">
                            {team?.map((teamData, index) => {
                                return (
                                    <AccordionItem key={index} eventKey={index}>
                                        <Accordion.Header onClick={() => setSelectTeam(teamData)}>
                                            <img src={require("../../IMAGES/members.png")} style={{padding: "0 10px"}}/>
                                            {teamData}
                                        </Accordion.Header>

                                        {member?.map((memberData, memberIndex) => {
                                            return (
                                                <AccordionBody key={memberIndex}
                                                               onClick={() => handleMemberClick(memberIndex)}
                                                               className={memberIndex === clickedIndex ? "boldText" : ""}
                                                >
                                                    <img src={require("../../IMAGES/member.png")}
                                                         style={{padding: "0 12px"}}/>
                                                    {memberData.name} {memberData.position}
                                                </AccordionBody>
                                            )
                                        })}
                                    </AccordionItem>
                                )
                            })}
                        </Accordion>
                    </LeftSide>

                    <MidSide>
                        <ButtonGroup>
                            <Title>결재자</Title>
                            <ArrowButton onClick={addToApprovalTable}>
                                <img src={require("../../IMAGES/right-arrow.png")}/>
                            </ArrowButton>
                            <ArrowButton onClick={removeApprovalTable}>
                                <img src={require("../../IMAGES/left-arrow.png")}/>
                            </ArrowButton>
                        </ButtonGroup>

                        <ButtonGroup>
                            <Title>참조자</Title>
                            <ArrowButton onClick={addToReferTable}>
                                <img src={require("../../IMAGES/right-arrow.png")}/>
                            </ArrowButton>
                            <ArrowButton onClick={removeReferTable}>
                                <img src={require("../../IMAGES/left-arrow.png")}/>
                            </ArrowButton>
                        </ButtonGroup>
                    </MidSide>

                    <div>
                        <RightSide>
                            <Title>결재선 정보</Title>
                            <BookmarkLine>
                                <p>사용자 결재라인</p>
                                <select value={bookmarkId || ""}
                                        onChange={(e) => {
                                            const id = e.target.value;
                                            bookmarkInfo(id)
                                            setBookmarkId(id)
                                        }}>
                                    <option value="" selected disabled>결재라인 선택</option>
                                    {bookmarkList?.map((data, index) => {
                                        return (
                                            <option key={index} value={data.id}>
                                                {data.name}
                                            </option>
                                        )
                                    })}
                                </select>
                                <SmallButton className="button"
                                             onClick={() => removeBookmark(bookmarkId)}>삭제</SmallButton>
                            </BookmarkLine>

                            <div>
                                <FirstContainer>
                                    <Title>결재자</Title>
                                    <Table>
                                        <TableHead>
                                            <tr>
                                                <th>이름</th>
                                                <th>직급</th>
                                                <th>부서</th>
                                                <th>결재선</th>
                                            </tr>
                                        </TableHead>
                                        <TableBody>
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
                                        </TableBody>
                                    </Table>
                                </FirstContainer>
                            </div>

                            <SecondContainer>
                                <Title>참조자</Title>
                                <Table>
                                    <TableHead>
                                        <tr>
                                            <th>이름</th>
                                            <th>직급</th>
                                            <th>부서</th>
                                        </tr>
                                    </TableHead>
                                    <TableBody>
                                        {referMember?.map((memberData, index) => {
                                            return (
                                                <tr key={index}>
                                                    <td>{memberData?.name}</td>
                                                    <td>{memberData?.position}</td>
                                                    <td>{memberData?.team}</td>
                                                </tr>
                                            )
                                        })}
                                    </TableBody>
                                </Table>
                            </SecondContainer>

                            <BookmarkLine>
                                <p>사용자 결재라인 이름</p>
                                <input value={bookmarkName}
                                       onChange={(e) => setBookmarkName(e.target.value)}
                                />
                                <SmallButton className="button" onClick={addBookmark}>저장</SmallButton>
                            </BookmarkLine>
                        </RightSide>

                        <LowerButton>
                            <Button className="button" onClick={enterSignLine}>적용</Button>
                            <Button variant="secondary" style={{marginLeft: "15px"}}
                                    onClick={handleApprovalPathModalClose}>닫기</Button>
                        </LowerButton>
                    </div>
                </Contents>
            </Modal.Body>
        </ModalWrapper>
    )
}

export default ApprovalPathModal

const ModalWrapper = styled(Modal)`
  --bs-modal-width: 1000px;
  --bs-modal-border-color: #4429f2;
  --bs-modal-header-border-color: #4429f2;
`
const ModalHeader = styled(Modal.Header)`
  color: white;
  background: #4429f2;
`
const Contents = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 30px;
`
const Title = styled.p`
  font-weight: bold;
  font-size: 20px;
  margin-bottom: 1rem;
`
const RightSide = styled.div`
  border: solid 1px rgba(68, 41, 242, 0.4);
  border-radius: 15px;
  padding: 30px;
  height: 715px;
`
const LeftSide = styled(RightSide)`
  display: flex;
  flex-direction: column;
  width: 30%;
`
const MidSide = styled.div`
  width: 110px;
  display: flex;
  flex-direction: column;
  padding: 20px;
  justify-content: center;
`
const AccordionItem = styled(Accordion.Item)`
  --bs-accordion-border-color: lightgray;
  --bs-accordion-active-bg: rgba(125, 121, 242, 0.2);
  --bs-accordion-btn-focus-box-shadow: none;
  --bs-accordion-body-padding-y: 0.65rem;
  --bs-accordion-btn-icon: none;
  --bs-accordion-btn-active-icon: none;
`
const AccordionBody = styled(Accordion.Body)`
  margin-left: 10px;

  &:hover {
    color: #4429f2;
    font-weight: bold;
    cursor: pointer;
  }

  &.boldText {
    font-weight: bold;
    color: #4429f2;
  }
`
const ButtonGroup = styled.div`
  padding: 50px 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`
const ArrowButton = styled(Button)`
  border: solid 1px rgba(68, 41, 242, 0.4);
  background: none;
  --bs-btn-hover-bg: rgba(125, 121, 242, 0.2);
  --bs-btn-active-bg: #4429f2;
  height: 50px;
  margin: 5px 0;
`
const BookmarkLine = styled.div`
  display: flex;
  flex-direction: row;
  background: rgba(125, 121, 242, 0.2);
  border: solid 1px rgba(68, 41, 242, 0.4);
  border-radius: 10px;
  align-items: center;
  padding: 10px;
  justify-content: space-evenly;
`
const TableContainer = styled.div`
  margin: 50px auto;
  width: 500px;
`
const FirstContainer = styled(TableContainer)`
  height: 200px;
`
const SecondContainer = styled(TableContainer)`
  height: 150px;
`
const TableHead = styled.thead`
  th {
    color: #4429f2;
    font-weight: bold;
    font-size: 18px;
    background: rgba(125, 121, 242, 0.2);
    text-align: center;
  }
`
const TableBody = styled.tbody`
  td {
    text-align: center;
  }
`
const SmallButton = styled(Button)`
  padding: 6px;
`
const LowerButton = styled.div`
  float: right;
  padding: 15px 0;
`
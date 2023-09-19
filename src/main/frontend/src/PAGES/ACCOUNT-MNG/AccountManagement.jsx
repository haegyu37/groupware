import {Button} from "react-bootstrap";
import {useEffect, useRef, useState} from "react";
import TeamModal from "./Modals/TeamModal";
import useStore from "../../store";
import PositionModal from "./Modals/PositionModal";
import AccountModal from "./Modals/AccountModal";
import defaultProfileImage from "../../IMAGES/profile.jpg";
import {
    ACCOUNT_BLOCK_API,
    ACCOUNT_DELETE_API,
    ACCOUNT_EDIT_API,
    MEMBER_LIST_INFO_API
} from "../../constants/api_constans";
import fetcher from "../../fetcher";
import Swal from "sweetalert2";
import styled from "styled-components";
import {useNavigate} from "react-router-dom";
import {FORBIDDEN_COMPONENT} from "../../constants/component_constants";

function AccountManagement() {
    const imgRef = useRef();

    const [showAccountModal, setShowAccountModal] = useState(false);
    const [showTeamModal, setShowTeamModal] = useState(false);
    const [showPositionModal, setShowPositionModal] = useState(false);
    const [imgFile, setImgFile] = useState("");
    const [preview, setPreview] = useState("");
    const [password, setPassword] = useState("");
    const [team, setTeam] = useState("");
    const [position, setPosition] = useState("");
    const [member, setMember] = useState([]);

    const {myAccount, account, selectAccount, teamName, positionName, selectTeam, selectPosition}
        = useStore(state => state);
    const isAdmin = myAccount?.authority === "ADMIN";
    const navigate = useNavigate();

    useEffect(() => {
        if (!isAdmin) {
            navigate(FORBIDDEN_COMPONENT);
        }
    }, [])

    const profileImg = "http://localhost:8080/member/image?imageName=" + account.image;

    const fetchMemberList = () => {
        return fetcher.get(MEMBER_LIST_INFO_API)
            .then((res) => setMember(res.data))
    }

    const resetInput = () => {
        selectAccount("")
        selectTeam("")
        selectPosition("")
        setPreview(null)
    }

    useEffect(() => {
        resetInput()
        fetchMemberList()
    }, [])

    const saveImgFile = () => {
        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            setImgFile(file);
            setPreview(reader.result);
        }
    }

    const editID = (id) => {
        const formData = new FormData();
        formData.append('post', JSON.stringify({
            newPassword: password ? password : null,
            team: teamName ? teamName : null,
            position: positionName ? positionName : null
        }));
        formData.append('image', imgFile);
        fetcher.post(`${ACCOUNT_EDIT_API}/${id}`, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then(() => {
            Swal.fire({
                position: 'center',
                icon: 'success',
                title: '계정정보 수정 완료',
                showConfirmButton: false,
                timer: 1500
            })
            resetInput()
            fetchMemberList()
        })
    }

    const blockID = (id, authority) => {
        if (!id) {
            Swal.fire({
                title: "계정을 선택하세요",
                icon: 'warning',
            })
            return;
        }
        Swal.fire({
            title: "권한을 변경하시겠습니까?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                fetcher.post(ACCOUNT_BLOCK_API, {
                    id: id,
                    authority: authority
                })
                    .then(() => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '변경 완료',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        resetInput()
                        fetchMemberList()
                    })
            }
        })
    }

    const deleteID = (id) => {
        if (!id) {
            Swal.fire({
                title: "삭제 할 계정을 선택하세요",
                icon: 'warning',
            })
            return;
        }
        Swal.fire({
            title: "계정을 삭제하시겠습니까?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '삭제',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                fetcher.post(`${ACCOUNT_DELETE_API}/${id}`)
                    .then(() => {
                        Swal.fire({
                            position: 'center',
                            icon: 'success',
                            title: '계정 삭제 완료',
                            showConfirmButton: false,
                            timer: 1500
                        })
                        resetInput()
                        fetchMemberList()
                    })
            }
        })
    }

    return (
        <Wrapper>
            <Container>
                <Upper>
                    <p>계정관리</p>
                    <UpperButton>
                        <Button className="buttonAdmin"
                                style={{marginLeft: "15px"}}
                                onClick={() => setShowAccountModal(true)}
                        >불러오기</Button>
                        <div>
                            {account && account?.authority === "BLOCK" ?
                                <Button className="buttonAdmin"
                                        onClick={() => blockID(account.id, "USER")}>차단해지</Button>
                                : <Button className="buttonAdmin"
                                          onClick={() => blockID(account.id, "BLOCK")}>접속차단</Button>
                            }
                            <Button className="buttonAdmin" style={{marginLeft: "25px"}}
                                    onClick={() => deleteID(account.id)}
                            >삭제</Button>
                        </div>
                    </UpperButton>
                </Upper>

                <Contents>
                    <Table>
                        <tbody>
                        <tr>
                            <th></th>
                            <ProfileImg>
                                <img src={account?.image ? (preview || profileImg) : (preview || defaultProfileImage)}
                                     alt="프로필 이미지"
                                />
                                <ProfileImgLabel htmlFor="profileImg">이미지 업로드</ProfileImgLabel>
                                <input
                                    style={{display: "none"}}
                                    type="file"
                                    accept="image/*"
                                    id="profileImg"
                                    onChange={saveImgFile}
                                    ref={imgRef}
                                />
                            </ProfileImg>
                        </tr>
                        <tr>
                            <th>이름</th>
                            <td>{account?.name}</td>
                        </tr>
                        <tr>
                            <th>비밀번호</th>
                            <td>
                                <input value={account.password}
                                       type="password"
                                       onChange={(e) => setPassword(e.target.value)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>사번</th>
                            <td>{account?.no}</td>
                        </tr>
                        <tr>
                            <th>부서</th>
                            <td>
                                <input value={teamName || account?.team}
                                       placeholder="부서를 선택하세요"
                                       onChange={(e) => setTeam(e.target.value)}/>
                                <IconImg src={require("../../IMAGES/more.png")}
                                         onClick={() => setShowTeamModal(true)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>직급</th>
                            <td>
                                <input value={positionName || account?.position}
                                       placeholder="직급을 선택하세요"
                                       onChange={(e) => setPosition(e.target.value)}
                                />
                                <IconImg src={require("../../IMAGES/more.png")}
                                         onClick={() => setShowPositionModal(true)}
                                />
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                </Contents>
                <SubmitButton>
                    <Button className="buttonAdmin" onClick={() => editID(account.id)}>수정</Button>
                </SubmitButton>
            </Container>

            <AccountModal showAccountModal={showAccountModal} fetchMemberList={fetchMemberList}
                          handleAccountModalClose={() => setShowAccountModal(false)}/>
            <TeamModal showTeamModal={showTeamModal} handleTeamModalClose={() => setShowTeamModal(false)}/>
            <PositionModal showPositionModal={showPositionModal}
                           handlePositionModalClose={() => setShowPositionModal(false)}/>
        </Wrapper>
    )
}

export default AccountManagement

export const Wrapper = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
`
export const Container = styled.div`
  min-width: 700px;
  width: 35%;
  height: 1000px;
  min-height: 800px;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
  font-weight: bold;
  color: #fa3e0c;
  background: rgba(250, 62, 12, 0.05);
  border: 1px solid rgba(250, 62, 12, 0.4);
  border-radius: 20px;
`
export const Upper = styled.div`
  padding: 0 50px;
  font-size: 24px;
`
const UpperButton = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  margin-top: 10px;
`
export const Contents = styled.div`
  height: 60%;
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-evenly;
`
export const Table = styled.table`
  tr {
    text-align: left;
  }

  th {
    width: 100px;
    height: 50px;
    color: #fa3e0c;
    font-weight: bold;
  }

  td {
    width: 250px;
    font-weight: normal;
    color: #000000;
    text-align: left;

  }
`
export const ProfileImg = styled.td`
  padding-bottom: 30px;

  img {
    width: 150px;
    height: 190px;
    margin-bottom: 20px;
    border: 3px solid rgba(250, 62, 12, 0.4);
  }
`
export const ProfileImgLabel = styled.label`
  width: 150px;
  height: 37px;
  font-weight: normal;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  background: #fa3e0c;
  border-radius: 7px;
  cursor: pointer;
`
export const IconImg = styled.img`
  width: 40px;
  height: 40px;
  cursor: pointer;
  margin-left: 10px;
`
export const SubmitButton = styled.div`
  display: flex;
  justify-content: center;
`
import React, {useEffect, useRef, useState} from "react";
import {Button} from "react-bootstrap";
import {CREATE_ID_API} from "../../constants/api_constans";
import fetcher from "../../fetcher";
import TeamModal from "./Modals/TeamModal";
import PositionModal from "./Modals/PositionModal";
import useStore from "../../store";
import defaultProfileImage from "../../IMAGES/profile.jpg";
import Swal from "sweetalert2";
import {
    Container,
    Contents,
    IconImg,
    ProfileImg,
    ProfileImgLabel,
    SubmitButton,
    Table, Upper,
    Wrapper
} from "./AccountManagement";
import {useNavigate} from "react-router-dom";
import {FORBIDDEN_COMPONENT} from "../../constants/component_constants";

function AccountRegistration() {
    const imgRef = useRef();

    const [showTeamModal, setShowTeamModal] = useState(false);
    const [showPositionModal, setShowPositionModal] = useState(false);
    const [imgFile, setImgFile] = useState("");
    const [preview, setPreview] = useState("");
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const [memberNo, setMemberNo] = useState("")

    const {myAccount, teamName, positionName, selectTeam, selectPosition} = useStore(state => state);
    const isAdmin = myAccount?.authority === "ADMIN";
    const navigate = useNavigate();

    useEffect(()=>{
        if(!isAdmin) {
            navigate(FORBIDDEN_COMPONENT);
        }
    },[])

    const saveImgFile = () => {
        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            setImgFile(file);
            setPreview(reader.result);
        }
    }

    const createID = () => {
        Swal.fire({
            title: "계정을 등록 하시겠습니까?",
            icon: 'question',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: '확인',
            cancelButtonText: '취소'
        }).then((result) => {
            if (result.isConfirmed) {
                const formData = new FormData();
                formData.append('post', JSON.stringify({
                    name: name,
                    password: password,
                    no: memberNo,
                    position: positionName,
                    team: teamName
                }));
                formData.append('image', imgFile);
                fetcher.post(CREATE_ID_API, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data'
                    }
                })
                    .then(() => {
                        resetInput();
                        Swal.fire({
                            position: 'mid',
                            icon: 'success',
                            title: '계정 등록 완료',
                            showConfirmButton: false,
                            timer: 1500
                        })
                    })
                    .catch(() => {
                        Swal.fire({
                            title: "값이 올바르지 않습니다.",
                            icon: 'warning',
                        })
                    })
            }
        })
    }

    const resetInput = () => {
        setName("")
        setPassword("")
        setMemberNo("")
        selectTeam("")
        selectPosition("")
        setPreview(null)
    }

    return (
        <Wrapper>
            <Container>
                <Upper>
                    <p>계정등록</p>
                </Upper>

                <Contents>
                    <Table>
                        <tbody>
                        <tr>
                            <th></th>
                            <ProfileImg>
                                <img src={preview ? preview : defaultProfileImage} alt="프로필 이미지"/>
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
                            <td>
                                <input value={name}
                                       onChange={(e) => setName(e.target.value)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>비밀번호</th>
                            <td>
                                <input value={password}
                                       type="password"
                                       onChange={(e) => setPassword(e.target.value)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>사번</th>
                            <td>
                                <input value={memberNo}
                                       onChange={(e) => setMemberNo(e.target.value)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>부서</th>
                            <td>
                                <input value={teamName} placeholder="부서를 선택하세요"/>
                                <IconImg src={require("../../IMAGES/more.png")}
                                         onClick={() => setShowTeamModal(true)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>직급</th>
                            <td>
                                <input value={positionName} placeholder="직급을 선택하세요"/>
                                <IconImg src={require("../../IMAGES/more.png")}
                                         onClick={() => setShowPositionModal(true)}
                                />
                            </td>
                        </tr>
                        </tbody>
                    </Table>
                </Contents>
                <SubmitButton>
                    <Button className="buttonAdmin" onClick={createID}>등록</Button>
                </SubmitButton>
            </Container>

            <TeamModal showTeamModal={showTeamModal} handleTeamModalClose={() => setShowTeamModal(false)}/>
            <PositionModal showPositionModal={showPositionModal}
                           handlePositionModalClose={() => setShowPositionModal(false)}/>
        </Wrapper>
    )
}

export default AccountRegistration
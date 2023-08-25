import {useEffect, useRef, useState} from "react";
import styles from "./AccountManagement.module.css";
import {Button, Modal} from "react-bootstrap";
import {CREATE_ID_API} from "../../constants/api_constans";
import fetcher from "../../fetcher";
import TeamModal from "./Modals/TeamModal";
import PositionModal from "./Modals/PositionModal";
import useStore from "../../store";
import Swal from "sweetalert2";

function AccountRegistration() {
    const imgRef = useRef();

    const [showTeamModal, setShowTeamModal] = useState(false);
    const [showPositionModal, setShowPositionModal] = useState(false);
    const [imgFile, setImgFile] = useState("");
    const [name, setName] = useState("");
    const [password, setPassword] = useState("");
    const [memberNo, setMemberNo] = useState("")

    const {teamName, positionName, selectTeam, selectPosition} = useStore(state => state);

    const saveImgFile = () => {
        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            setImgFile(reader.result);
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
                // const formData = new FormData();
                // formData.append('post', JSON.stringify({
                //     name: name,
                //     password: password,
                //     no: memberNo,
                //     position: positionName,
                //     team: teamName
                // }));
                // formData.append('image', imgFile);
                //
                // fetcher.post(CREATE_ID_API, formData, {
                //     headers: {
                //         'Content-Type': 'multipart/form-data'
                //     }
                // })
                fetcher.post(CREATE_ID_API, {
                    image:imgFile,
                    name: name,
                    password: password,
                    no: memberNo,
                    position: positionName,
                    team: teamName
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
    }

    return (
        <div className={styles.wrapper}>
            <div className={styles.container}>
                <div className={styles.upper}>
                    <div>
                        계정등록
                    </div>
                </div>

                <div className={styles.contents}>
                    <table className={styles.table}>
                        <tbody>
                        <tr>
                            <th></th>
                            <td className={styles.profile}>
                                <img src={imgFile ? imgFile : require("../../IMAGES/profile.jpg")}
                                     alt="프로필 이미지"
                                />
                                <label className={styles.profileImgLabel}
                                       htmlFor="profileImg"
                                >이미지 업로드</label>
                                <input
                                    style={{display: "none"}}
                                    type="file"
                                    accept="image/*"
                                    id="profileImg"
                                    onChange={saveImgFile}
                                    ref={imgRef}
                                />
                            </td>
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
                                <input value={teamName}/>
                                <img src={require("../../IMAGES/more.png")}
                                     className={styles.icon}
                                     onClick={() => setShowTeamModal(true)}
                                />
                            </td>
                        </tr>
                        <tr>
                            <th>직급</th>
                            <td>
                                <input value={positionName}/>
                                <img src={require("../../IMAGES/more.png")}
                                     className={styles.icon}
                                     onClick={() => setShowPositionModal(true)}
                                />
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div className={styles.modify}>
                    <Button className="buttonAdmin" onClick={createID}>등록</Button>
                </div>
            </div>

            <TeamModal showTeamModal={showTeamModal} handleTeamModalClose={() => setShowTeamModal(false)}/>
            <PositionModal showPositionModal={showPositionModal}
                           handlePositionModalClose={() => setShowPositionModal(false)}/>
        </div>
    )
}

export default AccountRegistration
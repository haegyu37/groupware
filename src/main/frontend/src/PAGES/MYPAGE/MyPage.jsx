import {useRef, useState} from "react";
import useStore from "../../store";
import {Button} from "react-bootstrap";
import fetcher from "../../fetcher";
import defaultProfileImage from "../../IMAGES/profile.jpg";
import {MY_INFO_API, MY_INFO_CHANGE_API} from "../../constants/api_constans";
import Swal from "sweetalert2";
import styled from "styled-components";
import {
    Container,
    Contents,
    ProfileImg,
    ProfileImgLabel,
    SubmitButton,
    Table,
    Upper,
    Wrapper
} from "../ACCOUNT-MNG/AccountManagement";

function MyPage() {
    const [password, setPassword] = useState("");
    const [imgFile, setImgFile] = useState("");
    const [preview, setPreview] = useState("");
    const imgRef = useRef();
    const {myAccount, setMyAccountInfo} = useStore(state => state);

    const profileImg = "http://localhost:8080/member/image?imageName=" + myAccount.image;

    const resetInput = () => {
        setPassword("")
    }

    const saveImgFile = () => {
        const file = imgRef.current.files[0];
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            setImgFile(file);
            setPreview(reader.result);
        }
    }

    const editInfo = () => {
        const formData = new FormData();
        formData.append('post', JSON.stringify({
            newPassword: password ? password : null,
        }));
        formData.append('image', imgFile);
        fetcher.post(MY_INFO_CHANGE_API, formData, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        })
            .then(() => {
                fetcher.get(MY_INFO_API)
                    .then((res) => setMyAccountInfo(res.data))
                resetInput()
                Swal.fire({
                    position: 'mid',
                    icon: 'success',
                    title: '수정 완료',
                    showConfirmButton: false,
                    timer: 1500
                })
            })
    }

    return (
        <Wrapper>
            <UserContainer>
                <Upper>
                    <p>마이페이지</p>
                </Upper>
                <Contents>
                    <UserTable>
                        <tbody>
                        <tr>
                            <th></th>
                            <UserProfileImg>
                                <img src={myAccount.image ? (preview || profileImg) : (preview || defaultProfileImage) }
                                     alt="프로필 이미지"
                                />
                                <UserProfileImgLabel htmlFor="profileImg">이미지 업로드</UserProfileImgLabel>
                                <input
                                    style={{display: "none"}}
                                    type="file"
                                    accept="image/*"
                                    id="profileImg"
                                    onChange={saveImgFile}
                                    ref={imgRef}
                                />
                            </UserProfileImg>
                        </tr>
                        <tr>
                            <th>이름</th>
                            <td>{myAccount.name}</td>
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
                            <td>{myAccount.no}</td>
                        </tr>
                        <tr>
                            <th>부서</th>
                            <td>{myAccount.team}</td>
                        </tr>
                        <tr>
                            <th>직급</th>
                            <td>{myAccount.position}</td>
                        </tr>
                        </tbody>
                    </UserTable>
                </Contents>
                <SubmitButton>
                    <Button className="button"
                            onClick={editInfo}
                    >수정</Button>
                </SubmitButton>
            </UserContainer>
        </Wrapper>
    )
}

export default MyPage

const UserContainer = styled(Container)`
  color: #4429f2;
  background: rgba(125, 121, 242, 0.05);
  border: solid 1px rgba(68, 41, 242, 0.4);
`
const UserProfileImg = styled(ProfileImg)`
  img {
    border: solid 3px rgba(68, 41, 242, 0.4);
  }
`
const UserProfileImgLabel = styled(ProfileImgLabel)`
  background: #4429f2;
`
const UserTable = styled(Table)`
  th {
    color: #4429f2;
  }
`
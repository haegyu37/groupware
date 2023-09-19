import {useEffect, useState} from "react";
import {Alert} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useCookies} from "react-cookie";
import fetcher from "../fetcher";
import {ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE} from "../constants/constants";
import {LOGIN_API} from "../constants/api_constans";
import {MAIN_COMPONENT} from "../constants/component_constants";
import styled from "styled-components";
import loginImg from "../IMAGES/login.jpg"

function LogIn() {
    const [memberNo, setMemberNo] = useState("")
    const [password, setPassword] = useState("")
    const [alertMessage, setAlertMessage] = useState("");
    const [isShowAlert, setIsShowAlert] = useState(false);

    const [ac, setAccessCookie,] = useCookies([ACCESS_TOKEN_COOKIE]);
    const [rc, setRefreshCookie,] = useCookies([REFRESH_TOKEN_COOKIE]);

    const navigate = useNavigate();

    const pressEnter = (e) => {
        if(e.key === 'Enter') {
            loginBtn();
        }
    };

    //Refresh token: Cookie 저장, Access token: Read-Only cookie 필요시마다 호출해서 사용
    const loginBtn = () => {
        if (!memberNo || !password) {
            setAlertMessage("사번 또는 비밀번호를 입력하세요.");
            setIsShowAlert(true);
            return
        }
        fetcher.post(LOGIN_API, {
            no: memberNo,
            password: password
        })
            .then((res) => {
                setAccessCookie(ACCESS_TOKEN_COOKIE, res?.data?.accessToken);
                setRefreshCookie(REFRESH_TOKEN_COOKIE, res?.data?.refreshToken);
                navigate(MAIN_COMPONENT);
            })
            .catch((err) => {
                const status = err?.response?.status;
                switch (status) {
                    case 401 :
                        setAlertMessage("입력하신 값이 올바르지 않습니다.");
                        break;
                    case 403 :
                        setAlertMessage("접속이 차단된 계정입니다.");
                        break;
                    case 410 :
                        setAlertMessage("삭제된 계정입니다.");
                        break;
                    default :
                        setAlertMessage("오류가 발생했습니다.");
                }
                setIsShowAlert(true);
            })
    }

    useEffect(() => {
        let timeout;
        if (isShowAlert) {
            timeout = setTimeout(() => {
                setIsShowAlert(false);
            }, 3000);
        }
        return () => clearTimeout(timeout);
    }, [isShowAlert]);

    return (
        <Wrapper>
            <Container>
                {isShowAlert && (
                    <LoginAlert variant="danger">
                        {alertMessage}
                    </LoginAlert>
                )}
                <LoginContainer>
                    <Title>ACCOUNT LOGIN</Title>
                    <InputWrapper>
                        <input type="text"
                               className="form-control"
                               id="email"
                               placeholder="사번"
                               value={memberNo}
                               onChange={(e) => setMemberNo(e.currentTarget.value)}
                        />
                        <input type="password"
                               className={`form-control`}
                               style={{margin: "15px 0"}}
                               id="password"
                               placeholder="비밀번호"
                               value={password}
                               onKeyDown={(e)=>pressEnter(e)}
                               onChange={(e) => setPassword(e.currentTarget.value)}
                        />
                        <SubmitButton type="submit"
                                      className={`btn btn-primary`}
                                      id="send"
                                      onClick={loginBtn}
                        >로그인</SubmitButton>
                    </InputWrapper>
                </LoginContainer>
            </Container>
        </Wrapper>
    )
}

export default LogIn

const Wrapper = styled.div`
  background: url(${loginImg}) no-repeat center fixed;
  background-size: cover;
  height: 100%;
  width: 100%;
`
const Container = styled.div`
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  display: flex;
`
const LoginContainer = styled.div`
  background: rgba(0, 0, 0, 0.93);
  border-radius: 15px;
  display: flex;
  flex-direction: column;
  min-height: fit-content;
  height: 350px;
  padding: 60px;
`
const Title = styled.div`
  color: #FFFFFF;
  font-weight: 600;
  font-size: 30px;
  text-align: center;
`
const InputWrapper = styled.div`
  width: 300px;
  padding-top: 30px;
`
const SubmitButton = styled.button`
  text-align: center;
  width: 300px;
  height: 45px;
  color: #000000;
  --bs-btn-bg: #afb0b1;
  --bs-btn-hover-bg: #afb0b1;
  --bs-btn-active-bg: #afb0b1;
  --bs-btn-color: #000000;
  --bs-btn-active-color: #000000;
  background: #afb0b1;
  border: none;

  &:hover {
    background: linear-gradient(90deg, rgba(194, 117, 245, 1) 0%, rgba(112, 34, 171, 1) 44%, rgba(14, 5, 187, 1) 100%);
    border: none;
  }
`
const LoginAlert = styled(Alert)`
  position: absolute;
  width: 300px;
  height: 50px;
  margin: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  right: calc(50% - 150px);
  bottom: calc(50% + 200px);
  --bs-alert-bg: #e82139;
  --bs-alert-color: white;
  --bs-alert-border-color: #e82139;
`
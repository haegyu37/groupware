import {useEffect, useState} from "react";
import styles from "./Login.module.css"
import {Alert} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {useCookies} from "react-cookie";
import fetcher from "../fetcher";
import {ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE} from "../constants/constants";
import {LOGIN_API} from "../constants/api_constans";
import {MAIN_COMPONENT} from "../constants/component_constants";

function LogIn() {
    const [memberNo, setMemberNo] = useState("")
    const [password, setPassword] = useState("")
    const [alertMessage, setAlertMessage] = useState("");
    const [isShowAlert, setIsShowAlert] = useState(false);

    const [ac, setAccessCookie,  ] = useCookies([ACCESS_TOKEN_COOKIE]);
    const [rc, setRefreshCookie, ] = useCookies([REFRESH_TOKEN_COOKIE]);

    const navigate = useNavigate();

    // useEffect(()=>{
    //     if (ac && rc){
    //         navigate(MAIN_COMPONENT)
    //     }
    // },[])

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
                    case 401 : setAlertMessage("입력하신 값이 올바르지 않습니다.");
                        break;
                    case 403 : setAlertMessage("접속이 차단된 계정입니다.");
                        break;
                    default : setAlertMessage("오류가 발생했습니다.");
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
        <div className={styles.wrapper}>
            <div className={styles.container}>
                {isShowAlert && (
                    <div>
                        <Alert variant="danger" className={styles.alert}>
                            {alertMessage}
                        </Alert>
                    </div>
                )}
                <div className={styles.loginContainer}>
                    <div className={styles.title}>ACCOUNT LOGIN</div>
                    <div className={styles.inputWrap}>
                        <div>
                            <input type="text"
                                   className="form-control"
                                   id="email"
                                   placeholder="사번"
                                   value={memberNo}
                                   onChange={(e) => setMemberNo(e.currentTarget.value)}
                            />
                        </div>
                        <div>
                            <input type="password"
                                   className={`form-control ${styles.m10}`}
                                   id="password"
                                   placeholder="비밀번호"
                                   value={password}
                                   onChange={(e) => setPassword(e.currentTarget.value)}
                            />
                        </div>
                        <button type="submit"
                                className={`${styles.button} btn btn-primary`}
                                id="send"
                                onClick={loginBtn}
                        >로그인</button>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default LogIn
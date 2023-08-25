import './App.css';
import MainContents from "./COMPONENT/MAIN/MainContents";
import Page from "./PAGES/Page";
import {Route, Routes, useLocation, useNavigate} from "react-router-dom";
import LogIn from "./PAGES/LogIn";
import NotFoundPage from "./PAGES/NotFoundPage";
import {
    FORBIDDEN_COMPONENT, LOGIN_COMPONENT, MAIN_COMPONENT,
    NOT_FOUND_COMPONENT, PAGE_COMPONENT
} from "./constants/component_constants";
import {useCookies} from "react-cookie";
import {ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE} from "./constants/constants";
import {useEffect} from "react";
import fetcher from "./fetcher";
import {MY_INFO_API} from "./constants/api_constans";
import useStore from "./store";
import Swal from "sweetalert2";
import ForbiddenPage from "./PAGES/ForbiddenPage";

function App() {
    const {setMyAccountInfo} = useStore(state => state)
    const navigate = useNavigate();
    const [accessToken, ,] = useCookies([ACCESS_TOKEN_COOKIE]);
    const [refreshToken, ,] = useCookies([REFRESH_TOKEN_COOKIE])

    useEffect(() => {
        if (!accessToken[ACCESS_TOKEN_COOKIE] || !refreshToken[REFRESH_TOKEN_COOKIE]) {
            if (window.location.pathname !== LOGIN_COMPONENT) {
                Swal.fire({
                    title: "로그인이 필요합니다.",
                    icon: 'warning',
                })
                navigate(LOGIN_COMPONENT);
            }
        }
    }, [accessToken, refreshToken])

    useEffect(() => {
        if (accessToken[ACCESS_TOKEN_COOKIE]) {
            fetcher.get(MY_INFO_API)
                .then((res) => setMyAccountInfo(res.data))
        }
    }, [accessToken])

    return (
        <div className="App">
            <Routes>
                <Route path={LOGIN_COMPONENT} element={<LogIn/>}/>
                <Route path={MAIN_COMPONENT} element={<MainContents/>}/>
                <Route path={PAGE_COMPONENT} element={<Page/>}/>
                <Route path={NOT_FOUND_COMPONENT} element={<NotFoundPage/>}/>
                <Route path={FORBIDDEN_COMPONENT} element={<ForbiddenPage/>}/>
            </Routes>
        </div>
    );
}

export default App;

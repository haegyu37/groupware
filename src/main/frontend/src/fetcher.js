import Cookies from "universal-cookie/es6";
import axios from "axios";
import {ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE} from "./constants/constants";
import Swal from "sweetalert2";
import {RENEW_TOKEN_API} from "./constants/api_constans";
import {LOGIN_COMPONENT} from "./constants/component_constants";

const cookies = new Cookies();

const INSTANCE = () => {
    const instance = {
        baseURL: "http://localhost:8080",
        timeout: 100000,
    }
    const accessToken = cookies.get(ACCESS_TOKEN_COOKIE);

    if(accessToken) {
        const axiosWithAuthHeader = {...instance};
        axiosWithAuthHeader.headers = {"Authorization" : `Bearer ${accessToken}`}
        return axios.create(axiosWithAuthHeader)
    } else {
        return axios.create(instance);
    }
}

function refreshRequest(originalRequest) {
    return originalRequest
        .then((res) => res) // 200
        .catch((err) => { // 401, 500, 기타 등등
            const config = err.config;
            if (err?.response?.data === "EXPIRED") {
                return INSTANCE().post(RENEW_TOKEN_API, {
                    accessToken: cookies.get(ACCESS_TOKEN_COOKIE),
                    refreshToken: cookies.get(REFRESH_TOKEN_COOKIE)
                })
                    .then((res) => {
                        if (res?.data?.accessToken && res?.data?.refreshToken) {
                            cookies.set(ACCESS_TOKEN_COOKIE, res?.data?.accessToken);
                            cookies.set(REFRESH_TOKEN_COOKIE, res?.data?.refreshToken);
                            return axios({
                                ...config,
                                headers: {"Authorization": `Bearer ${cookies.get(ACCESS_TOKEN_COOKIE)}`}
                            }).then((res) => res)
                        } else {
                            Swal.fire({
                                title: "토큰이 만료되었습니다.",
                                icon: 'warning',
                            })
                            window.location.href = LOGIN_COMPONENT
                            cookies.remove(ACCESS_TOKEN_COOKIE)
                            cookies.remove(REFRESH_TOKEN_COOKIE)
                        }
                    }).catch(() => {
                        alert("몰라용")
                        window.location.href = LOGIN_COMPONENT
                        cookies.remove(ACCESS_TOKEN_COOKIE)
                        cookies.remove(REFRESH_TOKEN_COOKIE)
                    })
            } else {
                throw err
            }
        })
}

export default {
    get : (url, options)=>{
        const originalRequest = INSTANCE().get(url,options);
        return refreshRequest(originalRequest);
    },
    delete : (url, options)=>{
        const originalRequest = INSTANCE().delete(url,options);
        return refreshRequest(originalRequest);
    },
    post : (url,data, options)=>{
        const originalRequest = INSTANCE().post(url,data,options);
        return refreshRequest(originalRequest);
    },
    put : (url,data, options)=>{
        const originalRequest = INSTANCE().put(url,data,options);
        return refreshRequest(originalRequest);
    }
}
import axios from "axios";

function createInstance(url) {
    return axios.create({
        baseURL: `${process.env.VUE_APP_API_URL}${url}`
    });
}


// header에 jwt 토큰 정보 불필요
export const api = createInstance("");
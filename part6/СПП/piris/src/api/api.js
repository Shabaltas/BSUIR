import * as axios from "axios";
import configs from "../configs";

const apiInstance = axios.create({
    withCredentials: true,
    headers: {
        "api-key": "1b44dd06-2645-4fda-bc7a-6c8f3e5f9b32"
    },
    baseURL: configs.apiUrl
});

const api = {
    authMe() {
        //return Promise.reject("yes, authme failed, u r loser");
        return apiInstance.get(configs.apiAuthMeEndpoint).then(resp => resp.data);
    },
    getUsers(page, pageSize) {
        return apiInstance.get(`${configs.apiUsersEndpoint}?page=${page}&count=${pageSize}`)
            .then(res => res.data);
    },
    followUser(userId) {
        return apiInstance.post(`${configs.apiFollowEndpoint}/${userId}`)
            .then(res => res.data);
    },
    unfollowUser(userId) {
        return apiInstance.delete(`${configs.apiFollowEndpoint}/${userId}`)
            .then(res => res.data);
    },
    getProfileInfo(userId){
        return apiInstance.get(`${configs.apiProfileEndpoint}/${userId}`)
            .then(res => res.data);
    },
    getProfileStatus(userId) {
        return apiInstance.get(`${configs.apiProfileEndpoint}/status/${userId}`)
            .then(res => res.data);
    },
    updateProfileStatus(status) {
        return apiInstance.put(`${configs.apiProfileEndpoint}/status`, {status})
            .then(res => res.data);
    },
    updateProfile(profile) {
        return apiInstance.put(`${configs.apiProfileEndpoint}`, profile)
            .then(res => res.data);
    },
    savePhoto(photoFile) {
        let formData = new FormData();
        formData.append('photo', photoFile);
        return apiInstance.put(`${configs.apiProfileEndpoint}/photo`, formData, {'Content-Type': 'multipart/form-data'})
            .then(res => res.data)
    },
    login(email, password, rememberMe = false, captcha = null) {
        return apiInstance.post(`${configs.apiLoginEndpoint}`, {email, password, rememberMe, captcha})
            .then(res => res.data)
    },
    logout() {
        return apiInstance.post(`${configs.apiLogoutEndpoint}`)
            .then(res => res.data)
    },
    getCaptchaUrl() {
        return apiInstance.post(`${configs.apiCaptchaEndpoint}`)
            .then(res => res.data.url)
    }
};

export default api;
